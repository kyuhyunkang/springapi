#!/bin/bash

echo "=== Reset Database ==="
docker exec springboot-sns-example-postgres psql -U sns -d sns -c "TRUNCATE users, follows, follow_counts RESTART IDENTITY CASCADE;"
echo "Done!"
