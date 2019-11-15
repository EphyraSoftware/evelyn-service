resource "tls_private_key" "web_entry_point_key" {
  algorithm   = "ECDSA"
  ecdsa_curve = "P384"
}

resource "tls_self_signed_cert" "web_entry_point_certificate" {
  key_algorithm   = tls_private_key.web_entry_point_key.algorithm
  private_key_pem = tls_private_key.web_entry_point_key.private_key_pem

  subject {
    common_name  = "service.evelyn.internal"
    organization = "EphyraSoftware"
  }

  dns_names = ["service.evelyn.internal"]

  validity_period_hours = 2190 // Three months

  allowed_uses = [
    "key_encipherment",
    "digital_signature",
    "server_auth",
  ]
}

resource "local_file" "key" {
  content     = tls_private_key.web_entry_point_key.private_key_pem
  filename = "${path.module}/tls.key"
}

resource "local_file" "certificate" {
  content     = tls_self_signed_cert.web_entry_point_certificate.cert_pem
  filename = "${path.module}/tls.crt"
}

resource "local_file" "password" {
  filename = "password.secret.txt"
  # TODO put somewhere else.
  content = "password"
}

resource "null_resource" "pkcs_bundle" {
  depends_on = [local_file.key, local_file.certificate, local_file.password]
  provisioner "local-exec" {
    command = "openssl pkcs12 -export -inkey tls.key -in tls.crt -name web-entry-point -out web-entry-point-keystore.p12 -passout file:password.secret.txt"
  }
}
