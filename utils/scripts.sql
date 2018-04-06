-- Selecione a porção desejada do script antes de executar

-- Execute o script a seguir se o objetivo for processar a coleta
SELECT screen_name, text FROM users, tweet WHERE users.id_str = tweet.users_id_str;
-- AND DATE(created_at_datetime) = '2017-07-13'  -- Caso seja necessário limitar a um dia 
-- utilize a opção de Export acima dos dados retornados para gerar o .csv

-- Execute o script a seguir se o objetivo for montar a visualização interativa c3.js
SELECT created_at_datetime, text FROM tweet WHERE users.id_str = tweet.users_id_str 
-- AND DATE(created_at_datetime) = '2017-07-13'  -- Caso seja necessário limitar a um dia 
 INTO OUTFILE 'C:/Users/eduardo/Desktop/MySQL_Exports/data.txt'
 FIELDS TERMINATED BY ''
 ENCLOSED BY '个'
 LINES TERMINATED BY '\r\n';
-- e troque o kanji no arquivo gerado por '|^' usando o notepad++

-- útil para montar o ranking de tweets mais retuitados
SELECT retweet_count, text FROM tweet 
ORDER BY retweet_count DESC;
