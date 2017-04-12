#!/bin/bash

#adding single message for konrad
curl -H "Content-Type: application/json" -X POST -d 'first konrad message' http://localhost:8080/messages/konrad -w '\n'

#adding second message for konrad
curl -H "Content-Type: application/json" -X POST -d 'second konrad message' http://localhost:8080/messages/konrad -w '\n'

#display messages for konrad which are sorted in reverse chronological order
curl http://localhost:8080/messages/konrad -w '\n'

#konrad does not follow any user so return no messages
curl http://localhost:8080/follow/konrad -w '\n'


#add single message for tom
curl -H "Content-Type: application/json" -X POST -d 'message from tom' http://localhost:8080/messages/tom -w '\n'

#add single message for alice
curl -H "Content-Type: application/json" -X POST -d 'message from alice' http://localhost:8080/messages/alice -w '\n'

#add single message for bob
curl -H "Content-Type: application/json" -X POST -d 'message from bob' http://localhost:8080/messages/bob -w '\n'

#konrad starts following tom
curl -H "Content-Type: application/json" -X POST -d 'tom' http://localhost:8080/follow/konrad -w '\n'

#konrad starts following alice
curl -H "Content-Type: application/json" -X POST -d 'alice' http://localhost:8080/follow/konrad -w '\n'

#konrad display 2 messages of followers, message from bob is not included
curl http://localhost:8080/follow/konrad -w '\n'

#some error handling cases
#konrad tries to add message over 140 characters, and error is returned and http status 413
curl -H "Content-Type: application/json" -v -X POST -d 'very long message very long message very long message very long message very long message very long message very long message very long message very long message very long message very long message very long message very long message very long message very long message very long message very long message very long message very long message very long message very long message very long message very long message ' http://localhost:8080/messages/konrad -w '\n'

#konrad tries to follow non-exising user, 404 not found status is returned and error
curl -v -H "Content-Type: application/json" -X POST -d 'nonExistingUser' http://localhost:8080/follow/konrad -w '\n'

#non existing user tries to follow some other user, 404 not found status is returned and error
curl -v -H "Content-Type: application/json" -X POST -d 'alice' http://localhost:8080/follow/someOtherNonExistingUser -w '\n'

#non existing user tries to display their messages
curl http://localhost:8080/messages/someNotExistingUser -w '\n'