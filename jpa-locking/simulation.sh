#!/usr/bin/env bash
set -euo pipefail

GAME_URL=http://localhost:8082

echo "=========="
echo "setup game"
echo "----------"
curl -i -X POST ${GAME_URL}/api/games -H 'Content-Type: application/json' -d '{ "numLives": 3 }'

echo
echo "=============="
echo "create players"
echo "--------------"
PLAYER1_ID=$(curl -s -X POST ${GAME_URL}/api/players -H 'Content-Type: application/json' -d '{ "username": "player1" }' | jq -r '.id')
echo "player 1 id = ${PLAYER1_ID}"
PLAYER2_ID=$(curl -s -X POST ${GAME_URL}/api/players -H 'Content-Type: application/json' -d '{ "username": "player2" }' | jq -r '.id')
echo "player 2 id = ${PLAYER2_ID}"

collect_stars() {
  local player_id=$1 num_stars=$2
  curl -s --write-out '\n' -X POST "${GAME_URL}/api/players/${player_id}/stars" \
    -H 'Content-Type: application/json' \
    -d "{ \"numStars\": ${num_stars} }" &
}

redeem_life() {
  local player_id=$1
  curl -s --write-out '\n' -X POST "${GAME_URL}/api/players/${player_id}/lives" &
}

echo "===================="
echo "player collect stars"
echo "--------------------"
collect_stars "${PLAYER1_ID}" 40
collect_stars "${PLAYER1_ID}" 40
collect_stars "${PLAYER2_ID}" 40
collect_stars "${PLAYER2_ID}" 40
wait
echo "--- stars collected ---"

echo
echo "==================="
echo "player redeem stars"
echo "-------------------"
redeem_life "${PLAYER1_ID}"
redeem_life "${PLAYER1_ID}"
redeem_life "${PLAYER2_ID}"
redeem_life "${PLAYER2_ID}"
redeem_life "${PLAYER2_ID}"
redeem_life "${PLAYER1_ID}"
wait
echo "--- lives redeemed ---"

echo
echo "==========="
echo "player info"
curl -s ${GAME_URL}/api/players/${PLAYER1_ID} | jq '.'
curl -s ${GAME_URL}/api/players/${PLAYER2_ID} | jq '.'
