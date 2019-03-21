#!/usr/bin/env bash

GAME_URL=http://localhost:8082

echo
echo "setup game"
echo "----------"
curl -i -X POST ${GAME_URL}/api/games -H 'Content-Type: application/json' -d '{ "numLives": 2 }'

echo
echo "create player 1"
echo "---------------"
PLAYER1_ID=$(curl -s -X POST ${GAME_URL}/api/players -H 'Content-Type: application/json' -d '{ "username": "player1", "numStars": 50 }' | jq -r '.id')
echo "player 1 id = ${PLAYER1_ID}"

echo
echo "create player 2"
echo "---------------"
PLAYER2_ID=$(curl -s -X POST ${GAME_URL}/api/players -H 'Content-Type: application/json' -d '{ "username": "player2", "numStars": 50 }' | jq -r '.id')
echo "player 2 id = ${PLAYER2_ID}"

echo
echo "add stars to player"
echo "-------------------"
curl -i -X POST ${GAME_URL}/api/players/${PLAYER1_ID}/stars -H 'Content-Type: application/json' -d '{ "numStars": 50 }'
curl -i -X POST ${GAME_URL}/api/players/${PLAYER2_ID}/stars -H 'Content-Type: application/json' -d '{ "numStars": 50 }'

echo
echo
echo "player redeem stars"
echo "-------------------"
curl -i -X POST ${GAME_URL}/api/players/${PLAYER1_ID}/lives & curl -i -X POST ${GAME_URL}/api/players/${PLAYER2_ID}/lives
curl -i -X POST ${GAME_URL}/api/players/${PLAYER1_ID}/lives & curl -i -X POST ${GAME_URL}/api/players/${PLAYER2_ID}/lives

echo
echo
echo "player info"
curl -s ${GAME_URL}/api/players/${PLAYER1_ID} | jq '.'
curl -s ${GAME_URL}/api/players/${PLAYER2_ID} | jq '.'
