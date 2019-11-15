pushd tls || exit
terraform init
terraform apply

kubectl -n evelyn-services create secret generic web-entry-point-keystore --from-file=./web-entry-point-keystore.p12
popd || exit

kubectl apply -n evelyn-services -f deploy.yml
