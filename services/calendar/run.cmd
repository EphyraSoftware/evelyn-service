call ..\..\gradlew buildimage
call ..\..\gradlew githubdockerpush
call kubectl -n evelyn-services delete -f deploy.yml
call kubectl -n evelyn-services apply -f deploy.yml
call kubectl -n evelyn-services get pods
