pipenv sync
pipenv run pip freeze > requirements.txt
pack build todo-expiry-daemon --builder cloudfoundry/cnb:cflinuxfs3
del requirements.txt
