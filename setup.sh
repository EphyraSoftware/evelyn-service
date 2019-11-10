kubectl create secret docker-registry ghregcred --namespace evelyn-services --docker-server=https://docker.pkg.github.com/ephyrasoftware/evelyn-service --docker-username=ThetaSinner --docker-password=<your-pword> --docker-email=greatdjonfire@hotmail.co.uk
kubectl apply -f deploy.yaml

pushd server/evelyn-web-entry-point || exit
kubectl apply -f deploy.yaml
popd || exit


