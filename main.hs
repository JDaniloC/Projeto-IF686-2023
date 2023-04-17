module Main where

import Control.Concurrent ( threadDelay, forkIO, newMVar,
                            putMVar, takeMVar, MVar )
import Text.Printf ( printf )
import Control.Monad ( when )

-- [x] A máquina possui 1000ml de cada tipo
-- [x] Cada cliente consome 300ml em cada execução
-- [x] Cada cliente demora 1000ms para encher o copo
-- [x] Só é possível realizar uma única ação por vez (encher/refil)
-- [x] Quando a máquina tiver menos de 1000ml do tipo, ele faz o refil
-- [x] O refil demora 1500ms bloqueando a máquina

-- Nome dos refrigerantes
refri1 :: String
refri1 = "Pepsi-Cola"
refri2 :: String
refri2 = "Guarana Polo Norte"
refri3 :: String
refri3 = "Guaraná Quate"

main = do

  -- Uma única variável de controle que é a máquina[refri1, refri2, refri3]
  machineControl <- newMVar [2000, 2000, 2000]
  forkIO $ refilSystem machineControl

  forkIO $ client 1 refri1 machineControl
  forkIO $ client 2 refri2 machineControl
  forkIO $ client 3 refri2 machineControl
  forkIO $ client 4 refri3 machineControl
  forkIO $ client 5 refri3 machineControl
  forkIO $ client 6 refri3 machineControl
  forkIO $ client 7 refri3 machineControl
  forkIO $ client 8 refri3 machineControl

-- Pausa o programa em Xms
sleep :: Int -> IO()
sleep x = threadDelay (1000 * x)

-- Para o refil: pausa o programa e print a mensagem
refilMsg :: String -> Int -> IO()
refilMsg x y = do
  sleep 1500
  printf "O refrigerante %s foi reabastecido com 1000 ml, e agora possui %d\n" x y

-- Para o refil: Devolve o array somando 1000 aos valores abaixo de 1000
refil :: [Int] -> [Int]
refil [] = []
refil (a:as)
  | a <= 1000 = (a + 1000) : refil as
  | otherwise = a : refil as

-- Lógica do refil: Faz loop
-- 1. Espera variável de controle
-- 2. Faz o refil de cada valor em machineControl
-- 3. Para cada refil feito: sleep e print
-- 4. Atualiza variável de controle 
refilSystem :: MVar [Int] -> IO()
refilSystem control = loop
  where
    loop = do
      [a, b, c] <- takeMVar control
      let [x, y, z] = refil [a, b, c]
      when (a /= x) (refilMsg refri1 x)
      when (b /= y) (refilMsg refri2 y)
      when (c /= z) (refilMsg refri3 z)
      putMVar control [x, y, z]
      loop

-- Para o client: Devolve o array subtraindo 300 do item no índice
consume :: Int -> [Int] -> [Int]
consume = consumeLoop 0 where
  consumeLoop _ _ [] = []
  consumeLoop x y (a:as)
    | x == y = (a - 300) : consumeLoop (x + 1) y as
    | otherwise = a : consumeLoop (x + 1) y as

-- Para o client: Devolve o índice [0] a partir do nome [refri1]
indexByName :: String -> Int
indexByName x
  | x == refri1 = 0
  | x == refri2 = 1
  | otherwise = 2

-- Lógica do client: Faz loop
-- 1. Espera variável de controle
-- 2. Se machineControl[i] >= 300 então
-- 3. print, sleep e machineControl[i] -= 300
-- 4. Atualiza variável de controle 
client :: Int -> String -> MVar [Int] -> IO()
client id name control = loop (indexByName name)
  where
    loop index = do
      stock <- takeMVar control
      if stock!!index >= 300 then do
        printf "O cliente %d do refrigerante %s está enchendo seu copo\n" id name
        sleep 1000
        putMVar control (consume index stock)
      else
        putMVar control stock
      loop index
