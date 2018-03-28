# -*- coding: utf-8 -*-
from __future__ import unicode_literals
import json
from tweepy.streaming import StreamListener
from tweepy import OAuthHandler
from tweepy import Stream
import mysql.connector
import time

# Chaves Twitter
consumer_key="TylRm9enPbCcHrfYxdWV96VO9"
consumer_secret="bodXxOODpMCojI1HuktcpXoA4TnUlMrpD1I6PIxdGnm3Tv4svV"

access_token="3431933662-C3tEhfC5lS3FlnnnRea3DXS2xfUJJymh6dtofHo"
access_token_secret="DJeMvewNvDHiacboW6kFPu5tjl6GSiqKrKEhnNFsyuPgq"

# Classe Streaming
class StdOutListener(StreamListener):

    """
    Dados: vusers = usuarios, vtweets = tweets, ventities = hashtags e vplaces = lugares

    Funções:    *users->define os valores na variavel vusers
                *tweets->define os valores na variavel vtweets
                *entities->define os valores na variavel ventities
                *places->define os valores na variavel vplaces
                *salvarNoBanco->salva as informações previamente inseridas no banco de dados
                *limparDados->limpa os dados do tweet anterior
    """
    vusers = {'screen_name': '', 'id_str': '', 'followers_count': '', 'friends_count': ''}
    vtweets = {'id_str': '', 'text': '', 'created_at': '', 'favorite_count': '', 'lang': '',
               'retweeted': '', 'retweet_count': '', 'tweet_original': ''}
    ventities = {'hashtags': ''}
    vplaces = {'country': '', 'country_code': '', 'full_name': '', 'id_place': '', 'name_place': '', 'coordinates': ''}


    @staticmethod
    def users(screen_name, id_str, followers_count, friends_count):
        StdOutListener.vusers.__setitem__('screen_name', screen_name)
        StdOutListener.vusers.__setitem__('id_str', id_str)
        StdOutListener.vusers.__setitem__('followers_count', followers_count)
        StdOutListener.vusers.__setitem__('friends_count', friends_count)

    @staticmethod
    def tweets(id_str, text, created_at, favorite_count, lang, retweeted, retweet_count, tweet_original):
        StdOutListener.vtweets.__setitem__('id_str', id_str)
        StdOutListener.vtweets.__setitem__('text', text.replace("'", ""))
        StdOutListener.vtweets.__setitem__('created_at', created_at)
        StdOutListener.vtweets.__setitem__('favorite_count', favorite_count)
        StdOutListener.vtweets.__setitem__('lang', lang)
        StdOutListener.vtweets.__setitem__('retweeted', retweeted)
        StdOutListener.vtweets.__setitem__('retweet_count', retweet_count)
        StdOutListener.vtweets.__setitem__('tweet_original', tweet_original)

    @staticmethod
    def entities(hashtags):
        StdOutListener.ventities.__setitem__('hashtags', hashtags.replace("'", ""))

    @staticmethod
    def places(country, country_code, full_name, id_place, name_place, coordinates):
        StdOutListener.vplaces.__setitem__('country', country)
        StdOutListener.vplaces.__setitem__('country_code', country_code)
        StdOutListener.vplaces.__setitem__('full_name', full_name.replace("'", ""))
        StdOutListener.vplaces.__setitem__('id_place', id_place.replace("'", ""))
        StdOutListener.vplaces.__setitem__('name_place', name_place.replace("'", ""))
        StdOutListener.vplaces.__setitem__('coordinates', coordinates)

    @staticmethod
    def salvarNoBanco():
        con = mysql.connector.connect(user='root', password='',
                              host='127.0.0.1',
                              database='databaseName')
        conexao = con.cursor()
        query = 'SET NAMES utf8mb4'
        conexao.execute(query)
        query = "INSERT INTO users VALUES(" + StdOutListener.vusers.get('id_str') + ", '" + \
                StdOutListener.vusers.get('screen_name') + "', " + \
                str(StdOutListener.vusers.get('followers_count')) + ", " + \
                str(StdOutListener.vusers.get('friends_count')) + ") ON DUPLICATE KEY UPDATE screen_name = VALUES(screen_name), " \
                                                                  "followers_count = VALUES(followers_count), " \
                                                                  "friends_count = VALUES(friends_count)"
        conexao.execute(query)

        if StdOutListener.vplaces.get('id_place')!='':
            query = "INSERT INTO places VALUES('" + StdOutListener.vplaces.get('id_place') +\
                    "', '" + StdOutListener.vplaces.get('country') +\
                    "', '" + StdOutListener.vplaces.get('country_code') +\
                    "', '" + StdOutListener.vplaces.get('full_name') +\
                    "', '" + StdOutListener.vplaces.get('name_place') +\
                    "', '" + str(StdOutListener.vplaces.get('coordinates')) + "') ON DUPLICATE KEY UPDATE country = VALUES(country)," \
                                                                              " country_code = VALUES(country_code)," \
                                                                              " full_name = VALUES(full_name)," \
                                                                              " name_place = VALUES(name_place)"
            conexao.execute(query)
            if(StdOutListener.vtweets.get('current_tweet_retweeted')==None):
                StdOutListener.vtweets.__setitem__('current_tweet_retweeted', '0')
            query = "INSERT INTO tweet VALUES(0000, '" + str(StdOutListener.vtweets.get('id_str')) + "'" \
                    ", '" + StdOutListener.vtweets.get('text') + "'" \
                    ", '" + StdOutListener.vtweets.get('created_at') + "'" \
                    ", " + str(StdOutListener.vtweets.get('favorite_count')) + "" \
                    ", '" + StdOutListener.vtweets.get('lang') + "'" \
                    ", " + str(StdOutListener.vtweets.get('retweeted')) + "" \
                    ", " + str(StdOutListener.vtweets.get('retweet_count')) + "" \
                    ", " + str(StdOutListener.vusers.get('id_str')) + "" \
                    ", '" + str(StdOutListener.vplaces.get('id_place')) + "'" \
                    ", ' " + str(StdOutListener.vtweets.get('tweet_original')) + "'"\
                    ", str_to_date('" + StdOutListener.vtweets.get('created_at') + "', '%a %b %d %H:%i:%S +0000 %Y') ) ON DUPLICATE KEY UPDATE " \
                    "favorite_count = VALUES(favorite_count), " \
                    "retweeted = 1, " \
                    "retweet_count = VALUES(retweet_count)"
            conexao.execute(query)
        else:
            query = "INSERT INTO tweet VALUES(0000, '" + str(StdOutListener.vtweets.get('id_str')) + "'" \
                    ", '" + StdOutListener.vtweets.get('text') + "'" \
                    ", '" + StdOutListener.vtweets.get('created_at') + "'" \
                    ", " + str(StdOutListener.vtweets.get('favorite_count')) + "" \
                    ", '" + StdOutListener.vtweets.get('lang') + "'" \
                    ", " + str(StdOutListener.vtweets.get('retweeted')) + "" \
                    ", " + str(StdOutListener.vtweets.get('retweet_count')) + "" \
                    ", " + str(StdOutListener.vusers.get('id_str')) + "" \
                    ", NULL" \
                    ", ' " + str(StdOutListener.vtweets.get('tweet_original')) + "'" \
                    ", str_to_date('" + StdOutListener.vtweets.get('created_at') + "', '%a %b %d %H:%i:%S +0000 %Y') ) ON DUPLICATE KEY UPDATE " \
                    "favorite_count = VALUES(favorite_count), " \
                    "retweeted = 1, " \
                    "retweet_count = VALUES(retweet_count)"
            conexao.execute(query)

        if StdOutListener.ventities.get('hashtags')!='':
            query = "INSERT INTO entities VALUES(0000, '" + StdOutListener.ventities.get('hashtags') + "', '"+ str(StdOutListener.vtweets.get('id_str')) + "')"
            conexao.execute(query)
        con.commit()
        conexao.close()
        con.close()


    @staticmethod
    def limparDados():
        StdOutListener.vusers = {'screen_name': '', 'id_str': '', 'followers_count': '', 'friends_count': ''}
        StdOutListener.vtweets = {'id_str': '', 'text': '', 'created_at': '', 'favorite_count': '', 'lang': '',
                                  'retweeted': '', 'retweet_count': '', 'tweet_original': ''}
        StdOutListener.ventities = {'hashtags': ''}
        StdOutListener.vplaces = {'country': '', 'country_code': '', 'full_name': '', 'id_place': '', 'name_place': '', 'coordinates': ''}


    def on_data(self, data):
        
        StdOutListener.limparDados()
        lista = json.loads(data)
        lista['current_tweet_retweet'] = None
        tweetOrig = ''
        
        if 'retweeted_status' in lista:
            tweetOriginal = lista['retweeted_status']         
            tweetOrig = tweetOriginal['id']
            StdOutListener.tweets(tweetOriginal['id'], tweetOriginal['text'], tweetOriginal['created_at'], tweetOriginal['favorite_count'], tweetOriginal['lang'], 1, tweetOriginal['retweet_count'], '')
            StdOutListener.users(tweetOriginal['user']['screen_name'], tweetOriginal['user']['id_str'], tweetOriginal['user']['followers_count'], tweetOriginal['user']['friends_count'])
            if tweetOriginal['place']:
                StdOutListener.places(tweetOriginal['place']['country'], tweetOriginal['place']['country_code'], tweetOriginal['place']['full_name']
                                      , tweetOriginal['place']['id'], tweetOriginal['place']['name'], tweetOriginal['place']['bounding_box']['coordinates'])
            if lista['entities']['hashtags']:
                hashtags = lista['entities']['hashtags']
                hashtags = hashtags[0].get('text')
                StdOutListener.entities(hashtags)
            StdOutListener.salvarNoBanco()
            StdOutListener.limparDados()
        StdOutListener.tweets(lista['id'], lista['text'], lista['created_at'], lista['favorite_count'], lista['lang'], lista['retweeted'], lista['retweet_count'], tweetOrig)
        StdOutListener.users(lista['user']['screen_name'], lista['user']['id_str'], lista['user']['followers_count'], lista['user']['friends_count'])
        
        if lista['place']:
            StdOutListener.places(lista['place']['country'], lista['place']['country_code'], lista['place']['full_name']
                                  , lista['place']['id'], lista['place']['name'], lista['place']['bounding_box']['coordinates'])
        
        if lista['entities']['hashtags']:
            hashtags = lista['entities']['hashtags']
            hashtags = hashtags[0].get('text')
            StdOutListener.entities(hashtags)
        
        print(lista['text'])
        StdOutListener.salvarNoBanco()
        print('\n')
        return True

    def on_error(self, status):
        time.sleep(5)
        print('\nERRO\n')
        return True
        
if __name__ == '__main__':
    
    while True:
        try:
            l = StdOutListener()
            auth = OAuthHandler(consumer_key, consumer_secret)
            auth.set_access_token(access_token, access_token_secret)

            stream = Stream(auth, l)
            
            trackTerm = ['term1', 'term2', 'term...']
            stream.filter(track=trackTerm,languages=["pt"])
        except:
            continue
