package org.evelyn.library.authentication.config;

import io.etcd.jetcd.ByteSequence;
import io.etcd.jetcd.Client;
import io.etcd.jetcd.kv.GetResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Component
public class EtcdRemoteConfigRefresher implements IRemoteConfigRefresher {
  @Value("${org.evelyn.etcd-url}")
  private String etcdUrl;

  private final EtcdPropertySetter propertySetter;

  public EtcdRemoteConfigRefresher(EtcdPropertySetter propertySetter) {
    this.propertySetter = propertySetter;
  }

  @Override
  public void refresh() {
    Client client = Client.builder().endpoints(etcdUrl).build();
    var kvClient = client.getKVClient();

    propertySetter.getPropertyNames().forEach(propertyName -> {
      ByteSequence key = ByteSequence.from(propertyName.getBytes());
      CompletableFuture<GetResponse> getFuture = kvClient.get(key);
      try {
        GetResponse response = getFuture.get();
        if (response.getCount() != 0) {
          String value = response.getKvs().get(0).getValue().toString(Charset.defaultCharset());
          propertySetter.setProperty(propertyName, value);
        }
      } catch (InterruptedException | ExecutionException e) {
        // What do I want to do here?
      }
    });
  }
}
