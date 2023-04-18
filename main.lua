local function pokemonAttack(pokemon, enemy)
  while pokemon.life > 0 and enemy.life > 0 do
    local rand = math.random(1,20)
    if rand <= 10 then
	  enemy.life = enemy.life - 50
      print(pokemon.name .. " usou Choque do trovão")
    elseif rand <= 15 then
	  enemy.life = enemy.life - 100
      print(pokemon.name .. " usou Calda de ferro")
    elseif rand <= 18 then
	  enemy.life = enemy.life - 150
      print(pokemon.name .. " usou Investida de ferro")
    else
	  enemy.life = enemy.life - 200
      print(pokemon.name .. " usou Trovão")
    end
	print(enemy.name .. " possui " .. enemy.life .. "HP")
    coroutine.yield()
  end
end

pikachu = {name = "Pikachu", life = 800}
raichu = {name = "Raichu", life = 1000}

local pokemon1 = coroutine.create(pokemonAttack)
local pokemon2 = coroutine.create(pokemonAttack)

while coroutine.resume(pokemon1, pikachu, raichu) do
	coroutine.resume(pokemon2, raichu, pikachu)
end

if pikachu.life > 0 then
	print("Pikachu venceu!")
else
	print("Raichu venceu!")
end
