#!/usr/bin/env bash
cd keycloak/bin
./kcadm.sh config credentials --server http://localhost:8080/auth --realm master --user admin --password admin

USERID=$(./kcadm.sh create users -r kosinov-keycloak-realm -s username=test -s enabled=true -o --fields id | jq '.id' | tr -d '"')
echo $USERID
./kcadm.sh update users/$USERID/reset-password -r test -s type=password -s value=test -s temporary=false -n
./kcadm.sh add-roles --uusername test --rolename USER -r kosinov-keycloak-realm