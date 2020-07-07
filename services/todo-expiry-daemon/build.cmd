pipenv sync
pipenv run pip freeze > requirements.txt
pack build todo-expiry-daemon --builder cloudfoundry/cnb:cflinuxfs3
del requirements.txt

rem buildctl --addr tcp://edge.evelyn.internal:31015 --tlscacert .\ca.pem --tlscert .\cert.pem --tlskey .\key.pem build -frontend=dockerfile.v0 --local context=. --local dockerfile=. --output type=image,name=docker.pkg.github.com/ephyrasoftware/evelyn-service/to
rem    do-expiry-daemon,push=true
