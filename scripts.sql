-- Selecione a porção desejada do script antes de executar

SELECT created_at_datetime, text FROM users, tweet WHERE users.id_str = tweet.users_id_str 
-- AND DATE(created_at_datetime) = '2017-07-13'  -- Caso seja necessário limitar a um dia
-- ORDER BY created_at -- Geralmente útil quando 
 INTO OUTFILE 'C:/Users/eduardo/Desktop/MySQL_Exports/data.txt'
 FIELDS TERMINATED BY ''
 ENCLOSED BY '个'
 LINES TERMINATED BY '\r\n';

SELECT retweet_count, text FROM tweet 
ORDER BY retweet_count DESC;