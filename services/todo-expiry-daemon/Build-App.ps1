$buildCommand = "$env:EVELYN_CI_BUILDCTL --debug build --frontend=dockerfile.v0 --local context=. --local dockerfile=. --output type=image,name=registry.evelyn.internal/evelyn/todo-expiry-daemo,push=true"

Invoke-Expression -Command $buildCommand
