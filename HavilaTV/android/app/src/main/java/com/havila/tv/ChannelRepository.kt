package com.havila.tv

/**
 * Havila TV channel catalogue.
 * Direct URLs come from the supplied IPTV catalogue; playlist URLs remain fallback sources.
 */
object ChannelRepository {
    private fun localLogo(resourceName: String) = "android.resource://com.havila.tv/drawable/$resourceName"

    const val PLAYLIST_WAF = "https://iptv-org.github.io/iptv/regions/waf.m3u"
    const val PLAYLIST_WW = "https://iptv-org.github.io/iptv/regions/ww.m3u"
    const val PLAYLIST_SSA = "https://iptv-org.github.io/iptv/regions/ssa.m3u"
    const val PLAYLIST_EAF = "https://iptv-org.github.io/iptv/regions/eaf.m3u"
    const val PLAYLIST_AFR = "https://iptv-org.github.io/iptv/regions/afr.m3u"
    const val PLAYLIST_INT = "https://iptv-org.github.io/iptv/countries/int.m3u"
    const val PLAYLIST_ZW = "https://iptv-org.github.io/iptv/countries/zw.m3u"
    const val PLAYLIST_TZ = "https://iptv-org.github.io/iptv/countries/tz.m3u"
    const val PLAYLIST_ZA = "https://iptv-org.github.io/iptv/countries/za.m3u"
    const val PLAYLIST_PT = "https://iptv-org.github.io/iptv/countries/pt.m3u"
    const val PLAYLIST_NG = "https://iptv-org.github.io/iptv/countries/ng.m3u"
    const val PLAYLIST_MZ = "https://iptv-org.github.io/iptv/countries/mz.m3u"
    const val PLAYLIST_BR = "https://iptv-org.github.io/iptv/countries/br.m3u"
    const val PLAYLIST_UNDEFINED = "https://iptv-org.github.io/iptv/countries/undefined.m3u"

    val channels = listOf(
        // Notícias / News
        Channel("24-horas-spain", "24 Horas (Spain)", "Notícias", "Notícias", "https://ztnr.rtve.es/ztnr/1694255.m3u8", true, null, listOf(PLAYLIST_INT), listOf("24 Horas"), "News"),
        Channel("24-tv", "24 TV", "Notícias", "Notícias", "https://turkmedya-live.ercdn.net/tv24/tv24.m3u8", true, null, listOf(PLAYLIST_INT), listOf("24 TV"), "News"),
        Channel("africa24-english", "Africa 24 English", "Notícias", "Notícias africanas", "https://edge20.vedge.infomaniak.com/livecast/ik:africa24english/manifest.m3u8", true, null, listOf(PLAYLIST_AFR), listOf("Africa 24 English"), "News"),
        Channel("africanews-french", "Africanews French", "Notícias", "Notícias africanas", "https://cdn-euronews.akamaized.net/live/eds/africanews-fr/25050/index.m3u8", true, null, listOf(PLAYLIST_AFR), listOf("Africanews French"), "News"),
        Channel("al-arabiya-english", "Al Arabiya English", "Notícias", "Notícias internacionais", "https://live.alarabiya.net/alarabiapublish/english/playlist_dvr.m3u8", true, null, listOf(PLAYLIST_INT), listOf("Al Arabiya English"), "News"),
        Channel("al-jazeera-english", "Al Jazeera English", "Notícias", "Notícias internacionais", "https://live-hls-apps-aje-fa.getaj.net/AJE/index.m3u8", true, null, listOf(PLAYLIST_INT), listOf("Al Jazeera English"), "News"),
        Channel("bbc-news-africa", "BBC News Africa", "Notícias", "Notícias internacionais", "https://vs-cmaf-push-ww-live.akamaized.net/x=4/i=urn:bbc:pips:service:bbc_news_channel_hd/hevc_iptv_mse_v0.mpd", true, null, listOf(PLAYLIST_INT), listOf("BBC News Africa"), "News"),
        Channel("bbc-news-north-america", "BBC News North America", "Notícias", "Notícias internacionais", "http://23.237.104.106:8080/USA_BBC_NEWS/index.m3u8", true, null, listOf(PLAYLIST_INT), listOf("BBC News North America"), "News"),
        Channel("bbc-news-south-asia", "BBC News South Asia", "Notícias", "Notícias internacionais", "https://mumbai-edge.smartplaytv.in/BBCNews/index.m3u8", true, null, listOf(PLAYLIST_INT), listOf("BBC News South Asia"), "News"),
        Channel("cnn-international", "CNN International", "Notícias", "Notícias internacionais", "https://d3bp6dwmpbdajl.cloudfront.net/v1/master/3722c60a815c199d9c0ef36c5b73da68a62b09d1/cc-ury0meh5m4nzm/index.m3u8", true, null, listOf(PLAYLIST_INT), listOf("CNN International"), "News"),
        Channel("cnn-portugal", "CNN Portugal", "Notícias", "Notícias em português", "https://github.com/thomraider12/canaistvpt/raw/main/m3u8s/cnnpt.m3u8", true, null, listOf(PLAYLIST_PT), listOf("CNN Portugal"), "News"),
        Channel("dw-english", "DW English", "Notícias", "Informação internacional", "https://amg01644-amg01644c1-amgplt0343.playout.now3.amagi.tv/ts-eu-w1-n2/playlist/amg01644-amg01644c1-amgplt0343/playlist.m3u8", true, localLogo("dw_logo"), listOf(PLAYLIST_INT, PLAYLIST_AFR), listOf("DW English", "DW"), "News"),
        Channel("euronews-portuguese", "Euronews Portuguese", "Notícias", "Notícias em português", "https://jmp2.uk/plu-619e6614c9d9650007a2b171.m3u8", true, null, listOf(PLAYLIST_INT), listOf("Euronews Portuguese"), "News"),
        Channel("euronews-english", "Euronews English HD", "Notícias", "Notícias internacionais", "https://dash4.antik.sk/live/test_euronews/playlist.m3u8", true, null, listOf(PLAYLIST_INT), listOf("Euronews English"), "News"),
        Channel("france24-english", "France 24 English", "Notícias", "Notícias internacionais", "https://live.france24.com/hls/live/2037218-b/F24_EN_HI_HLS/master_5000.m3u8", true, null, listOf(PLAYLIST_INT), listOf("France 24 English", "France 24"), "News"),
        Channel("nhk-world-japan", "NHK World-Japan", "Notícias", "Notícias internacionais", "https://masterpl.hls.nhkworld.jp/hls/w/live/smarttv.m3u8", true, null, listOf(PLAYLIST_INT), listOf("NHK World-Japan"), "News"),
        Channel("noticias-rcn", "Noticias RCN", "Notícias", "Notícias", "https://jmp2.uk/plu-67d9b0ebc290c9499046e88f.m3u8", true, null, listOf(PLAYLIST_INT), listOf("Noticias RCN"), "News"),
        Channel("rtp-noticias", "RTP Noticias", "Notícias", "Notícias", "https://streaming-live.rtp.pt/livetvhlsDVR/rtpnHDdvr.smil/playlist.m3u8", true, null, listOf(PLAYLIST_PT), listOf("RTP Noticias"), "News"),

        // Notícias / Legislative
        Channel("actv", "ACTV", "Notícias", "Canal legislativo", "https://castus-vod-dev.s3.amazonaws.com/vod_clients/athens/live/ch2/video.m3u8", true, null, listOf(PLAYLIST_INT), listOf("ACTV"), "Legislative"),
        Channel("atxn2", "ATXN2", "Notícias", "Canal legislativo", "https://cdn-atxn-01.vos360.video/Content/HLS/Live/channel(f97b5ddc-b7e4-dff8-9f10-8c16d0833639)/index.m3u8", true, null, listOf(PLAYLIST_INT), listOf("ATXN2"), "Legislative"),
        Channel("batv-government", "BATV Government Channel", "Notícias", "Canal governamental", "https://livestream.telvue.com/billerica3/f7b44cfafd5c52223d5498196c8a2e7b.sdp/playlist.m3u8", true, null, listOf(PLAYLIST_INT), listOf("BATV Government Channel"), "Legislative"),
        Channel("canal-congreso", "Canal Congreso", "Notícias", "Canal legislativo", "http://138.121.15.230:9002/CANAL-CONGRESO/index.m3u8", true, null, listOf(PLAYLIST_INT), listOf("Canal Congreso"), "Legislative"),
        Channel("canal-gov", "Canal Gov", "Notícias", "Canal governamental", "http://45.177.114.115/TV_BRASIL_2/index.m3u8", true, null, listOf(PLAYLIST_BR), listOf("Canal Gov"), "Legislative"),
        Channel("tv-camara", "TV Camara", "Notícias", "Canal legislativo", "https://stream3.camara.gov.br/tv1/manifest.m3u8", true, null, listOf(PLAYLIST_BR), listOf("TV Camara"), "Legislative"),
        Channel("yuma-channel-72", "Yuma Channel 72", "Notícias", "Canal público", "https://livestream.telvue.com/yumaaz1/f7b44cfafd5c52223d5498196c8a2e7b.sdp/playlist.m3u8", true, null, listOf(PLAYLIST_INT), listOf("Yuma Channel 72"), "Legislative"),

        // Notícias / Business
        Channel("100-news", "100% NEWS", "Notícias", "Notícias e negócios", "http://85.238.112.40:8810/hls_sec/239.33.16.32-.m3u8", true, null, listOf(PLAYLIST_INT), listOf("100% NEWS"), "Business"),
        Channel("apart-tv", "apart TV", "Notícias", "Negócios e cultura", "https://2-fss-2.streamhoster.com/pl_124/206820-4338160-1/playlist.m3u8", true, null, listOf(PLAYLIST_INT), listOf("apart TV"), "Business"),
        Channel("bloomberg-originals", "Bloomberg Originals", "Notícias", "Negócios e economia", "https://bloomberg.com/media-manifest/streams/qt.m3u8", true, null, listOf(PLAYLIST_INT), listOf("Bloomberg Originals"), "Business"),
        Channel("bloomberg-asia", "Bloomberg TV Asia Live Event", "Notícias", "Negócios e economia", "https://bloomberg.com/media-manifest/streams/asia-event.m3u8", true, null, listOf(PLAYLIST_INT), listOf("Bloomberg TV Asia Live Event"), "Business"),
        Channel("cnbc-tv18", "CNBC TV18 Prime HD", "Notícias", "Negócios e economia", "https://n18syndication.akamaized.net/bpk-tv/CNBC_Tv18_Prime_HD_NW18_MOB/output01/index.m3u8", true, null, listOf(PLAYLIST_INT), listOf("CNBC TV18 Prime HD"), "Business"),
        Channel("negocios-tv", "Negocios TV", "Notícias", "Negócios e economia", "https://streaming013.gestec-video.com/hls/negociostv.m3u8", true, null, listOf(PLAYLIST_INT), listOf("Negocios TV"), "Business"),

        // Notícias / Public + local Mozambique entries from supplied file
        Channel("24-horas-public", "24 Horas", "Notícias", "Notícias públicas", "https://ztnr.rtve.es/ztnr/1694255.m3u8", true, null, listOf(PLAYLIST_INT), listOf("24 Horas"), "Public"),
        Channel("canal-sur-noticias", "Canal Sur Noticias", "Notícias", "Notícias públicas", "https://cdnlive.codev8.net/rtvalive/smil:channel42.smil/playlist.m3u8", true, null, listOf(PLAYLIST_INT), listOf("Canal Sur Noticias"), "Public"),
        Channel("tv-ufop", "TV UFOP", "Notícias", "Educação e serviço público", "https://app.tvufop.com.br/hls/tvufop.m3u8", true, null, listOf(PLAYLIST_BR), listOf("TV UFOP"), "Public"),
        Channel("uaa-tv", "UAA TV", "Notícias", "Educação e serviço público", "https://5fe2654d6127d.streamlock.net:443/uaa2/videouaa2/playlist.m3u8", true, null, listOf(PLAYLIST_INT), listOf("UAA TV"), "Public"),
        Channel("accuweather-now", "AccuWeather NOW", "Notícias", "Meteorologia", "https://cdn-ue1-prod.tsv2.amagi.tv/linear/amg00684-accuweather-accuweather-plex/playlist.m3u8", true, null, listOf(PLAYLIST_INT), listOf("AccuWeather NOW"), "Weather"),
        Channel("fox-weather", "Fox Weather", "Notícias", "Meteorologia", "https://247wlive.foxweather.com/stream/index.m3u8", true, null, listOf(PLAYLIST_INT), listOf("Fox Weather"), "Weather"),
        Channel("sky-news-weather", "Sky News Weather", "Notícias", "Meteorologia", "https://distro001-gb-hls1-prd.delivery.skycdp.com/easel_cdn/ngrp:weather_loop.stream_all/playlist.m3u8", true, null, listOf(PLAYLIST_INT), listOf("Sky News Weather"), "Weather"),
        Channel("weatherspy", "WeatherSpy", "Notícias", "Meteorologia", "https://jukin-weatherspy-2-au.samsung.wurl.tv/playlist.m3u8", true, null, listOf(PLAYLIST_INT), listOf("WeatherSpy"), "Weather"),
        Channel("mntv", "MNTV", "Notícias", "Negócios, economia e atualidade", "https://www.khuwemultimedia.com/hls/mntv/mntv.m3u8", true, null, listOf(PLAYLIST_MZ), listOf("MNTV"), "Business"),
        Channel("tvm", "TVM (Mozambique)", "Notícias", "Televisão pública moçambicana", "http://196.28.226.121:1935/live/smil:Channel1.smil/playlist.m3u8", true, localLogo("tvm_logo"), listOf(PLAYLIST_MZ), listOf("TVM", "TVM Moçambique"), "Public"),

        // Desporto / Sports
        Channel("espn4-brazil", "ESPN 4 (Brazil)", "Desporto", "Desporto", "http://181.78.197.59:8000/play/a07n/index.m3u8", true, null, listOf(PLAYLIST_BR), listOf("ESPN 4"), "Sports"),
        Channel("espn-brazil", "ESPN (Brazil)", "Desporto", "Desporto", "http://181.78.197.59:8000/play/a07z/index.m3u8", true, null, listOf(PLAYLIST_BR), listOf("ESPN Brazil"), "Sports"),
        Channel("fifa-plus-portuguese", "FIFA+ Portuguese", "Desporto", "Futebol e desporto", "https://e3be9ac5.wurl.com/master/f36d25e7e52f1ba8d7e56eb859c636563214f541/TEctYnJfRklGQVBsdXNQb3J0dWd1ZXNlX0hMUw/playlist.m3u8", true, null, listOf(PLAYLIST_INT), listOf("FIFA+ Portuguese"), "Sports"),
        Channel("ge-fast", "ge Fast", "Desporto", "Desporto", "https://amg00716-globo-amg00716c1-tcl-br-9495.playouts.now.amagi.tv/playlist.m3u8", true, null, listOf(PLAYLIST_BR), listOf("ge Fast"), "Sports"),
        Channel("n-sports", "N Sports", "Desporto", "Desporto", "https://ogc-nsprt-tcl-roku-syndication.otteravision.com/ogc/nsprt/nsprt.m3u8", true, null, listOf(PLAYLIST_BR), listOf("N Sports"), "Sports"),
        Channel("pluto-esportes", "Pluto TV Esportes", "Desporto", "Desporto", "https://jmp2.uk/plu-5f32d2db0af67400077f29c4.m3u8", true, null, listOf(PLAYLIST_BR), listOf("Pluto TV Esportes"), "Sports"),
        Channel("red-bull-tv-br", "Red Bull TV BR", "Desporto", "Desporto e aventura", "https://d03ae6b5c6724c24867e97a3dc04934a.mediatailor.us-west-2.amazonaws.com/v1/master/ba62fe743df0fe93366eba3a257d792884136c7f/LINEAR-1026-WORBBRPTFAST-WHALETVPLUS/1026/hls/master/playlist.m3u8", true, null, listOf(PLAYLIST_BR), listOf("Red Bull TV BR"), "Sports"),
        Channel("beinsports-xtra", "beIN SPORTS XTRA", "Desporto", "Desporto", "https://bein-xtra-bein.amagi.tv/playlist.m3u8", true, null, listOf(PLAYLIST_INT), listOf("beIN SPORTS XTRA"), "Sports"),
        Channel("cbs-sports-hq", "CBS Sports HQ", "Desporto", "Desporto", "https://jmp2.uk/plu-5e9f2c05172a0f0007db4786.m3u8", true, null, listOf(PLAYLIST_INT), listOf("CBS Sports HQ"), "Sports"),
        Channel("eurosport", "Eurosport", "Desporto", "Desporto", "https://drk6xq0vhn.gpcdn.net/live/euro_sports_hd_abr/index.m3u8", true, null, listOf(PLAYLIST_INT), listOf("Eurosport"), "Sports"),
        Channel("fifa-plus-us", "FIFA+ United States", "Desporto", "Futebol e desporto", "https://d2w9q46ikgrcwx.cloudfront.net/v1/master/3722c60a815c199d9c0ef36c5b73da68a62b09d1/cc-of5cbk3sav3w5/v1/sysdata_s_p_a_fifa_7/samsungheadend_us/latest/main/hls/playlist.m3u8", true, null, listOf(PLAYLIST_INT), listOf("FIFA+ United States"), "Sports"),
        Channel("fox-deportes", "Fox Deportes", "Desporto", "Desporto", "http://23.237.104.106:8080/USA_FOX_DEPORTES/index.m3u8", true, null, listOf(PLAYLIST_INT), listOf("Fox Deportes"), "Sports"),
        Channel("fuel-tv", "FUEL TV", "Desporto", "Desporto radical", "https://amg01074-fueltv-fueltvemeaen-rakuten-b6j62.amagi.tv/hls/amagi_hls_data_rakutenAA-fueltvemeaen/CDN/master.m3u8", true, null, listOf(PLAYLIST_INT), listOf("FUEL TV"), "Sports"),
        Channel("pluto-esports", "Pluto TV E-Sports", "Desporto", "E-sports", "https://jmp2.uk/plu-5ff3934600d4c7000733ff49.m3u8", true, null, listOf(PLAYLIST_INT), listOf("Pluto TV E-Sports"), "Sports"),
        Channel("sony-sports-ten-1", "Sony Sports Ten 1 HD", "Desporto", "Desporto", "http://103.185.24.134:3001/SonyTenSports1/index.m3u8", true, null, listOf(PLAYLIST_INT), listOf("Sony Sports Ten 1 HD"), "Sports"),
        Channel("viju-sport", "viju+ Sport", "Desporto", "Desporto", "http://stream.mcquack.net/333/index.m3u8", true, null, listOf(PLAYLIST_INT), listOf("viju+ Sport"), "Sports"),

        // Desporto / Outdoor + Auto
        Channel("agrocanal", "AgroCanal", "Desporto", "Conteúdo outdoor", "http://45.162.64.114/AGRO_CANAL/index.m3u8", true, null, listOf(PLAYLIST_BR), listOf("AgroCanal"), "Outdoor"),
        Channel("30a-darcizzle", "30A Darcizzle Offshore", "Desporto", "Outdoor", "https://30a-tv.com/darcizzle.m3u8", true, null, listOf(PLAYLIST_INT), listOf("30A Darcizzle Offshore"), "Auto"),
        Channel("agricultura-tv", "Agricultura TV", "Desporto", "Agricultura e outdoor", "https://redirector.rudo.video/hls-video/ey6283je82983je9823je8jowowiekldk9838274/921tv/921tv.smil/playlist.m3u8", true, null, listOf(PLAYLIST_INT), listOf("Agricultura TV"), "Auto"),
        Channel("agro-tv-peru", "Agro TV (Peru)", "Desporto", "Agricultura e outdoor", "https://live-evg8.tv360.bitel.com.pe/bitel/agroSRT/playlist.m3u8", true, null, listOf(PLAYLIST_INT), listOf("Agro TV"), "Auto"),
        Channel("agrotendencia", "Agrotendencia TV", "Desporto", "Agricultura e outdoor", "https://5fc584f3f19c8.streamlock.net/agrotendencia/videoagrotendencia_hls1/playlist.m3u8", true, null, listOf(PLAYLIST_INT), listOf("Agrotendencia TV"), "Auto"),
        Channel("akc-tv", "AKC TV", "Desporto", "Outdoor", "https://broadcast.blivenyc.com/speed/broadcast/22/desktop-playlist.m3u8", true, null, listOf(PLAYLIST_INT), listOf("AKC TV"), "Auto"),
        Channel("love-nature", "Love Nature", "Desporto", "Natureza e outdoor", "https://aegis-cloudfront-1.tubi.video/6d6d0f24-8445-4b4c-bdf6-44f9e38beaa4/playlist.m3u8", true, null, listOf(PLAYLIST_INT), listOf("Love Nature"), "Outdoor"),
        Channel("italian-fishing-tv", "Italian Fishing TV", "Desporto", "Pesca e outdoor", "https://fms.premio.link/fishingtv/playlist.m3u8", true, null, listOf(PLAYLIST_INT), listOf("Italian Fishing TV"), "Outdoor"),
        Channel("waterbear", "WaterBear", "Desporto", "Natureza e outdoor", "https://amg01415-waterbearnetwor-waterbear-samsunguk-1h0y8.amagi.tv/playlist/amg01415-waterbearnetwor-waterbear-samsunguk/playlist.m3u8", true, null, listOf(PLAYLIST_INT), listOf("WaterBear"), "Outdoor"),

        // Gospel / Religious
        Channel("angel-tv-portuguese", "Angel TV Portuguese", "Gospel", "Programação cristã", "https://janya-digimix.akamaized.net/vglive-sk-382409/portuese/ngrp:angelportuguese_all/playlist.m3u8", true, null, listOf(PLAYLIST_INT), listOf("Angel TV Portuguese"), "Religious"),
        Channel("adb-tv", "ADB TV", "Gospel", "Programação cristã", "https://live-tv.waytv.pt/adbtv/adbtv/playlist.m3u8", true, null, listOf(PLAYLIST_PT), listOf("ADB TV"), "Religious"),
        Channel("fonte-tv", "Fonte TV", "Gospel", "Programação cristã", "https://59fb88a1b1108.streamlock.net/fonte/_definst_/fontetv/chunklist_w1224511652.m3u8", true, null, listOf(PLAYLIST_BR), listOf("Fonte TV"), "Religious"),
        Channel("gospel-cartoon", "Gospel Cartoon", "Gospel", "Conteúdo cristão infantil", "https://stmv1.srvif.com/gospelcartoon/gospelcartoon/playlist.m3u8", true, null, listOf(PLAYLIST_BR), listOf("Gospel Cartoon"), "Religious"),
        Channel("gospel-movie-tv", "Gospel Movie TV", "Gospel", "Filmes cristãos", "https://stmv1.srvif.com/gospelf/gospelf/playlist.m3u8", true, null, listOf(PLAYLIST_BR), listOf("Gospel Movie TV"), "Religious"),
        Channel("impd-tv", "IMPD TV Worldwide", "Gospel", "Programação cristã", "https://igrejamundial.nuvemplay.live/hls/stream.m3u8", true, null, listOf(PLAYLIST_BR), listOf("IMPD TV Worldwide"), "Religious"),
        Channel("kk-tv", "KK TV", "Gospel", "Programação cristã", "https://w1.manasat.com/ktv-angola/smil:ktv-angola.smil/playlist.m3u8", true, null, listOf(PLAYLIST_AFR), listOf("KK TV"), "Religious"),
        Channel("kuriakos-tv", "Kuriakos TV", "Gospel", "Programação cristã", "https://w1.manasat.com/ktv/smil:ktv.smil/playlist.m3u8", true, null, listOf(PLAYLIST_PT), listOf("Kuriakos TV"), "Religious"),
        Channel("novo-tempo", "Novo Tempo", "Gospel", "Programação cristã", "http://45.162.64.114/NOVO_TEMPO/index.m3u8", true, null, listOf(PLAYLIST_BR), listOf("Novo Tempo"), "Religious"),
        Channel("plenitude-tv", "Plenitude TV", "Gospel", "Programação cristã", "https://strv.painelvox.top/plenitude/plenitude/playlist.m3u8", true, null, listOf(PLAYLIST_BR), listOf("Plenitude TV"), "Religious"),
        Channel("rede-gospel", "Rede Gospel", "Gospel", "Programação cristã", "https://cdn.live.br1.jmvstream.com/w/LVW-8719/LVW8719_AcLVAxWy5J/playlist.m3u8", true, null, listOf(PLAYLIST_BR), listOf("Rede Gospel"), "Religious"),
        Channel("rit-tv", "RIT TV", "Gospel", "Programação cristã", "https://acesso.ecast.site:3648/live/ritlive.m3u8", true, null, listOf(PLAYLIST_BR), listOf("RIT TV"), "Religious"),
        Channel("sbn-global", "SBN TV Global", "Gospel", "Programação cristã", "https://watch.sardius.media/channels/Ea867F3e1F56B06/site_8D363D13Eb/live.m3u8", true, null, listOf(PLAYLIST_INT), listOf("SBN TV Global"), "Religious"),
        Channel("tv-mana-1", "TV Mana 1", "Gospel", "Programação cristã", "https://w1.manasat.com/tvmana-1/smil:tvmana-1.smil/playlist.m3u8", true, null, listOf(PLAYLIST_INT), listOf("TV Mana 1"), "Religious"),
        Channel("3abn-proclaim", "3ABN Proclaim! Network", "Gospel", "Programação cristã", "https://3abn.bozztv.com/3abn2/Pro_live/smil:Pro_live.smil/playlist.m3u8", true, null, listOf(PLAYLIST_INT), listOf("3ABN Proclaim! Network"), "Religious"),
        Channel("angel-tv-africa", "Angel TV Africa", "Gospel", "Programação cristã", "https://janya-digimix.akamaized.net/vglive-sk-904559/africa/ngrp:angelafrica_all/playlist.m3u8", true, null, listOf(PLAYLIST_AFR), listOf("Angel TV Africa"), "Religious"),
        Channel("bethel-tv", "Bethel TV", "Gospel", "Programação cristã", "https://alfa.betheltv.tv/srt/3_abr/playlist.m3u8", true, null, listOf(PLAYLIST_INT), listOf("Bethel TV"), "Religious"),
        Channel("daystar-tv", "Daystar TV", "Gospel", "Programação cristã", "https://live-mcl.cdn01.net/smarttv/48vy43564/playlist.m3u8", true, null, listOf(PLAYLIST_INT), listOf("Daystar TV"), "Religious"),
        Channel("faith-africa", "Faith Africa", "Gospel", "Programação cristã", "https://faithnow-hls.secdn.net/faithnow-holland-channel/play/FaithAfrica.smil/playlist.m3u8", true, null, listOf(PLAYLIST_AFR), listOf("Faith Africa"), "Religious"),
        Channel("faith-world-tv", "Faith World TV", "Gospel", "Programação cristã", "https://faithworldtv.abs.tv/1826926230/index.m3u8", true, null, listOf(PLAYLIST_INT), listOf("Faith World TV"), "Religious"),
        Channel("goodnews-tv", "GoodNews TV", "Gospel", "Televisão cristã", "https://bpgdlwwar3ze-hls-live.wmncdn.net/goodnews/live.stream/playlist.m3u8", true, localLogo("goodnews_tv_logo"), listOf(PLAYLIST_INT), listOf("GoodNews TV"), "Religious"),
        Channel("good-news-world", "Good News World TV", "Gospel", "Programação baseada na fé", null, false, localLogo("goodnews_world_tv_logo"), listOf(PLAYLIST_WW, PLAYLIST_AFR, PLAYLIST_INT), listOf("Good News World TV", "Good News World TV Networks", "Good News World"), "Religious"),
        Channel("gospel-truth-tv", "Gospel Truth TV", "Gospel", "Programação cristã", "https://na.linear.zype.com/bf94f9f4-383c-40e0-9d49-279a4f33ab8d/f29b8836-d990-4c8d-b421-192a8f43edc4-zype/manifest.m3u8", true, null, listOf(PLAYLIST_INT), listOf("Gospel Truth TV"), "Religious"),
        Channel("hope-channel-international", "Hope Channel International", "Gospel", "Fé, família e educação", "https://jstre.am/live/jsl:cZXINbwrFj6.m3u8", true, localLogo("hope_channel_logo"), listOf(PLAYLIST_INT), listOf("Hope Channel International"), "Religious"),
        Channel("impact-channel", "Impact Channel", "Gospel", "Programação cristã", "https://5fe2654d6127d.streamlock.net/impactchannel/videoimpactchannel/playlist.m3u8", true, null, listOf(PLAYLIST_INT), listOf("Impact Channel"), "Religious"),
        Channel("its-supernatural", "It's Supernatural! Network", "Gospel", "Programação cristã", "https://content.uplynk.com/channel/b880a791bf95426d917a509e0c44796a.m3u8", true, null, listOf(PLAYLIST_INT), listOf("It's Supernatural! Network"), "Religious"),
        Channel("it-is-written", "It Is Written TV", "Gospel", "Programação cristã", "https://itiswrittentv.akamaized.net/xR8gyATuHMVx69u9G79DXnpNRL/playlist.m3u8", true, null, listOf(PLAYLIST_INT), listOf("It Is Written TV"), "Religious"),
        Channel("kuriakos-cine", "Kuriakos Cine", "Gospel", "Cinema cristão", "https://w2.manasat.com/kcine/smil:kcine.smil/playlist.m3u8", true, null, listOf(PLAYLIST_PT), listOf("Kuriakos Cine"), "Religious"),
        Channel("loveworldsat", "LoveworldSAT", "Gospel", "Programação cristã 24 horas", "https://cdnstack.internetmultimediaonline.org/lwsat/lwsat.stream/index.m3u8", true, localLogo("loveworld_sat_logo"), listOf(PLAYLIST_AFR, PLAYLIST_SSA, PLAYLIST_INT), listOf("LoveworldSAT", "LoveWorld SAT"), "Religious"),
        Channel("mcn-television", "MCN Television", "Gospel", "Programação cristã", "https://eu1.servers10.com:8081/8338/index.m3u8", true, null, listOf(PLAYLIST_INT), listOf("MCN Television"), "Religious"),
        Channel("miracle-tv", "Miracle TV", "Gospel", "Programação cristã", "https://viewmedia7219.bozztv.com/wmedia/viewmedia100/web_030/Stream/playlist.m3u8", true, null, listOf(PLAYLIST_INT), listOf("Miracle TV"), "Religious"),
        Channel("peniel-tv", "Peniel TV Biblia Abierta", "Gospel", "Programação cristã", "https://s.emisoras.tv:8081/penielbibliaabierta/index.m3u8", true, null, listOf(PLAYLIST_INT), listOf("Peniel TV Biblia Abierta"), "Religious"),
        Channel("salvation-tv-india", "Salvation TV (India)", "Gospel", "Programação cristã", "https://ktismaservers.in:3902/live/salvationtvlive.m3u8", true, null, listOf(PLAYLIST_INT), listOf("Salvation TV India"), "Religious"),
        Channel("sbn-international", "SBN TV International", "Gospel", "Programação cristã", "https://watch.sardius.media/channels/Ea867F3e1F56B06/site_09a4778E89/live.m3u8", true, null, listOf(PLAYLIST_INT), listOf("SBN TV International"), "Religious"),
        Channel("thedove-tv", "theDove TV", "Gospel", "Programação cristã", "https://thedovetv.bozztv.com/thedovetv/thedovetv/index.m3u8", true, null, listOf(PLAYLIST_INT), listOf("theDove TV"), "Religious"),
        Channel("tv-mana-mocambique", "TV Mana Mocambique", "Gospel", "Conteúdo cristão", "https://w2.manasat.com/tvmana-mz/smil:tvmana-mz.smil/playlist.m3u8", true, null, listOf(PLAYLIST_MZ), listOf("TV Mana Mocambique", "TV Mana Moçambique"), "Religious"),
        Channel("kk-tv-2", "KK TV", "Gospel", "Programação cristã", "https://w1.manasat.com/ktv-angola/smil:ktv-angola.smil/playlist.m3u8", true, null, listOf(PLAYLIST_AFR), listOf("KK TV"), "Religious"),
        Channel("acnn", "ACNN", "Gospel", "Programação cristã", "https://viewmedia7219.bozztv.com/wmedia/viewmedia100/web_014/Stream/playlist.m3u8", true, null, listOf(PLAYLIST_NG), listOf("ACNN"), "Religious"),
        Channel("yadah-tv", "Yadah TV", "Gospel", "Programação cristã", "https://viewmedia7219.bozztv.com/wmedia/viewmedia100/web_018/Stream/playlist.m3u8", true, null, listOf(PLAYLIST_ZW), listOf("Yadah TV"), "Religious"),

        // Filmes e Séries / Movies
        Channel("itage-tv", "Itage TV", "Filmes e Séries", "Filmes", "https://viewmedia7219.bozztv.com/wmedia/viewmedia100/web_011/Stream/playlist.m3u8", true, null, listOf(PLAYLIST_INT), listOf("Itage TV"), "Movies"),
        Channel("axn-movies-portugal", "AXN Movies (Portugal)", "Filmes e Séries", "Filmes", "http://50.7.120.34:8080/AXN_BLACK/index.m3u8", true, null, listOf(PLAYLIST_PT), listOf("AXN Movies"), "Movies"),
        Channel("axn-brazil", "AXN (Brazil)", "Filmes e Séries", "Filmes", "http://170.83.16.50/AXN/index.m3u8", true, null, listOf(PLAYLIST_BR), listOf("AXN Brazil"), "Movies"),
        Channel("filmes-suspense", "Filmes Suspense", "Filmes e Séries", "Filmes", "https://jmp2.uk/plu-5f171d3442a0500007362f22.m3u8", true, null, listOf(PLAYLIST_BR), listOf("Filmes Suspense"), "Movies"),
        Channel("mytime-movie", "MyTime Movie Network (Brazil)", "Filmes e Séries", "Filmes", "https://appletree-mytime-samsungbrazil.amagi.tv/playlist.m3u8", true, null, listOf(PLAYLIST_BR), listOf("MyTime Movie Network"), "Movies"),
        Channel("kuriakos-cine-movies", "Kuriakos Cine", "Filmes e Séries", "Cinema", "https://w2.manasat.com/kcine/smil:kcine.smil/playlist.m3u8", true, null, listOf(PLAYLIST_PT), listOf("Kuriakos Cine"), "Movies"),
        Channel("sony-movies", "Sony Movies", "Filmes e Séries", "Filmes", "http://45.162.64.114/SONY_MOVIES/index.m3u8", true, null, listOf(PLAYLIST_BR), listOf("Sony Movies"), "Movies"),
        Channel("telecine-action", "Telecine Action", "Filmes e Séries", "Filmes", "http://46.151.196.223:14326/", true, null, listOf(PLAYLIST_BR), listOf("Telecine Action"), "Movies"),
        Channel("pluto-ficcao-cientifica", "Pluto TV Ficcao Cientifica", "Filmes e Séries", "Filmes", "https://jmp2.uk/plu-5fa15ad6367e170007cdd098.m3u8", true, null, listOf(PLAYLIST_BR), listOf("Pluto TV Ficcao Cientifica"), "Movies"),
        Channel("awtv", "AWTV", "Filmes e Séries", "Séries", "https://awtv.nuvemplay.live/hls/stream.m3u8", true, null, listOf(PLAYLIST_BR), listOf("AWTV"), "Series"),
        Channel("macgyver", "MacGyver (United States)", "Filmes e Séries", "Séries clássicas", "https://jmp2.uk/plu-63eb9dc84e83e70008abea92.m3u8", true, null, listOf(PLAYLIST_INT), listOf("MacGyver"), "Series"),
        Channel("pluto-desenhos-classicos", "Pluto TV Desenhos Classicos", "Filmes e Séries", "Clássicos", "https://jmp2.uk/plu-655e5c4d2c46f3000877a54b.m3u8", true, null, listOf(PLAYLIST_BR), listOf("Pluto TV Desenhos Classicos"), "Series"),
        Channel("comedy-central-brasil", "Comedy Central Brasil", "Filmes e Séries", "Comédia", "https://stmv2.srvif.com/gafeab/gafeab/playlist.m3u8", true, null, listOf(PLAYLIST_BR), listOf("Comedy Central Brasil"), "Classic"),
        Channel("comedy-central-pluto", "Comedy Central Pluto TV BR", "Filmes e Séries", "Comédia", "https://jmp2.uk/plu-5f357e91b18f0b00073583d2.m3u8", true, null, listOf(PLAYLIST_BR), listOf("Comedy Central Pluto TV BR"), "Classic"),
        Channel("failarmy-brazil", "FailArmy Brazil", "Filmes e Séries", "Comédia", "https://jmp2.uk/plu-5f5141c1605ddf000748eb1b.m3u8", true, null, listOf(PLAYLIST_BR), listOf("FailArmy Brazil"), "Classic"),
        Channel("fora-tedio-tv", "Fora Tedio TV", "Filmes e Séries", "Comédia", "https://stmv2.srvif.com/foratedio/foratedio/playlist.m3u8", true, null, listOf(PLAYLIST_BR), listOf("Fora Tedio TV"), "Classic"),
        Channel("pluto-series-comedia", "Pluto TV Series Comedia", "Filmes e Séries", "Comédia e séries", "https://jmp2.uk/plu-655e5bc94261ca000810cb17.m3u8", true, null, listOf(PLAYLIST_BR), listOf("Pluto TV Series Comedia"), "Classic"),

        // Havila Kids / Kids + Animation
        Channel("gospel-cartoon-kids", "Gospel Cartoon", "Havila Kids", "Conteúdo infantil", "https://stmv1.srvif.com/gospelcartoon/gospelcartoon/playlist.m3u8", true, null, listOf(PLAYLIST_BR), listOf("Gospel Cartoon"), "Kids"),
        Channel("3abn-kids", "3ABN Kids", "Havila Kids", "Conteúdo infantil", "https://3abn.bozztv.com/3abn2/Kids_live/smil:Kids_live.smil/playlist.m3u8", true, null, listOf(PLAYLIST_INT), listOf("3ABN Kids"), "Kids"),
        Channel("god-stands-kids", "God Stands Kids Club TV", "Havila Kids", "Conteúdo infantil", "https://online.godstands.tv:5443/WebRTCApp/streams/kidsenglish.m3u8", true, null, listOf(PLAYLIST_INT), listOf("God Stands Kids Club TV"), "Kids"),
        Channel("kuriakos-kids", "Kuriakos Kids", "Havila Kids", "Conteúdo infantil", "https://w2.manasat.com/kkids/smil:kkids.smil/playlist.m3u8", true, null, listOf(PLAYLIST_PT), listOf("Kuriakos Kids"), "Kids"),
        Channel("logos-tv-kids", "Logos TV Kids", "Havila Kids", "Conteúdo infantil", "https://streamer1.streamhost.org/salive/logoskidsH/playlist.m3u8", true, null, listOf(PLAYLIST_INT), listOf("Logos TV Kids"), "Kids"),
        Channel("ebs-kids", "EBS Kids", "Havila Kids", "Conteúdo infantil", "https://ebsonair.ebs.co.kr/ebsufamilypc/familypc1m/playlist.m3u8", true, null, listOf(PLAYLIST_INT), listOf("EBS Kids"), "Kids"),
        Channel("90s-kids", "90's Kids", "Havila Kids", "Conteúdo infantil", "https://jmp2.uk/plu-6452c814939a590008567a3b.m3u8", true, null, listOf(PLAYLIST_INT), listOf("90's Kids"), "Kids"),
        Channel("brat-tv", "Brat TV", "Havila Kids", "Conteúdo juvenil", "https://streams2.sofast.tv/v1/master/611d79b11b77e2f571934fd80ca1413453772ac7/04072b68-dc6a-4d5e-98af-f356ba8d5063/playlist.m3u8", true, null, listOf(PLAYLIST_INT), listOf("Brat TV"), "Kids"),
        Channel("cartoon-network-latam", "Cartoon Network Latin America Brazil", "Havila Kids", "Animação", "http://15.204.246.24:8080/CartoonNetworkHD/index.m3u8", true, null, listOf(PLAYLIST_BR), listOf("Cartoon Network Latin America Brazil"), "Animation"),
        Channel("cctv14", "CCTV-14 HD", "Havila Kids", "Conteúdo infantil", "http://74.91.26.218:82/live/cctv14hd.m3u8", true, null, listOf(PLAYLIST_INT), listOf("CCTV-14 HD"), "Kids"),
        Channel("christian-youth-cyc", "Christian Youth Channel CYC", "Havila Kids", "Conteúdo juvenil", "http://media3.smc-host.com:1935/cycnow.com/smil:cyc.smil/playlist.m3u8", true, null, listOf(PLAYLIST_INT), listOf("Christian Youth Channel CYC"), "Kids"),
        Channel("deparinti", "DeParinti.ro", "Havila Kids", "Educação infantil", "https://live77cdn2.antenaplay.ro/live2_fast/ch4/stream.m3u8", true, null, listOf(PLAYLIST_INT), listOf("DeParinti.ro"), "Kids"),
        Channel("dios-te-ve-kids", "Dios Te Ve Kids", "Havila Kids", "Conteúdo infantil", "https://s.emisoras.tv:8081/diostevekids/index.m3u8", true, null, listOf(PLAYLIST_INT), listOf("Dios Te Ve Kids"), "Kids"),
        Channel("discovery-kids-latam", "Discovery Kids Latin America", "Havila Kids", "Animação infantil", "http://181.224.200.5:2277/play/a00o/index.m3u8", true, null, listOf(PLAYLIST_BR), listOf("Discovery Kids Latin America"), "Kids"),
        Channel("o-reino-infantil", "O Reino Infantil", "Havila Kids", "Conteúdo infantil", "https://jmp2.uk/plu-5f5c216df68f920007888315.m3u8", true, null, listOf(PLAYLIST_BR), listOf("O Reino Infantil"), "Kids"),
        Channel("turma-da-monica", "Turma da Monica", "Havila Kids", "Conteúdo infantil", "https://jmp2.uk/plu-5f997e44949bc70007a6941e.m3u8", true, null, listOf(PLAYLIST_BR), listOf("Turma da Monica"), "Kids"),
        Channel("pluto-tv-anime-br", "Pluto TV Anime BR", "Havila Kids", "Animação", "https://jmp2.uk/plu-5f12136385bccc00070142ed.m3u8", true, null, listOf(PLAYLIST_BR), listOf("Pluto TV Anime BR"), "Animation"),
        Channel("naruto-brazil", "Naruto Brazil", "Havila Kids", "Animação", "https://jmp2.uk/plu-5f6df5a173d7340007c559f7.m3u8", true, null, listOf(PLAYLIST_BR), listOf("Naruto Brazil"), "Animation"),
        Channel("discovery-kids-latam-animation", "Discovery Kids Latin America Brazil", "Havila Kids", "Animação", "http://15.204.246.24:8080/DiscoveryKidsHD/index.m3u8", true, null, listOf(PLAYLIST_BR), listOf("Discovery Kids Latin America Brazil"), "Animation"),

        // Música e Entretenimento / Music
        Channel("3abn-praise", "3ABN Praise Him Music Network", "Música e Entretenimento", "Música", "https://3abn.bozztv.com/3abn1/PraiseHim/smil:PraiseHim.smil/playlist.m3u8", true, null, listOf(PLAYLIST_INT), listOf("3ABN Praise Him Music Network"), "Music"),
        Channel("cbn-gospel", "CBN Gospel", "Música e Entretenimento", "Música gospel", "https://59d39900ebfb8.streamlock.net/ccbn/ccbn/playlist.m3u8", true, null, listOf(PLAYLIST_INT), listOf("CBN Gospel"), "Music"),
        Channel("harmony-gospel", "Harmony Gospel Music TV", "Música e Entretenimento", "Música gospel", "https://tgn.bozztv.com/watc57/watc57-harmony/watc57-harmony/index.m3u8", true, null, listOf(PLAYLIST_INT), listOf("Harmony Gospel Music TV"), "Music"),
        Channel("peniel-musical", "Peniel Musical", "Música e Entretenimento", "Música", "https://s.emisoras.tv:8081/penielmusical/index.m3u8", true, null, listOf(PLAYLIST_INT), listOf("Peniel Musical"), "Music"),
        Channel("spirit-tv", "Spirit TV", "Música e Entretenimento", "Música", "https://cdnlive.myspirit.tv/LS-ATL-43240-2/index.m3u8", true, null, listOf(PLAYLIST_INT), listOf("Spirit TV"), "Music"),
        Channel("trace-gospel", "Trace Gospel", "Música e Entretenimento", "Música gospel", "https://channels.trace.plus/Traceprod/GOSPEL_FR_hd/index.m3u8", true, null, listOf(PLAYLIST_INT), listOf("Trace Gospel"), "Music"),
        Channel("trace-gospel-nigeria-east-africa", "Trace Gospel Nigeria and East Africa", "Música e Entretenimento", "Música gospel", "https://channels.trace.plus/Traceprod/GOSPEL_ROA_hd/index.m3u8", true, null, listOf(PLAYLIST_AFR), listOf("Trace Gospel Nigeria and East Africa"), "Music"),
        Channel("trace-gospel-southern-africa", "Trace Gospel Southern Africa", "Música e Entretenimento", "Música gospel", "https://channels.trace.plus/Traceprod/GOSPEL_SA_hd/index.m3u8", true, null, listOf(PLAYLIST_AFR), listOf("Trace Gospel Southern Africa"), "Music"),
        Channel("muzangala-tv", "Muzangala TV", "Música e Entretenimento", "Música", "https://5cf4a2c2512a2.streamlock.net/tvmuzangala/tvmuzangala/playlist.m3u8", true, null, listOf(PLAYLIST_AFR), listOf("Muzangala TV"), "Music"),
        Channel("rmc-life", "RMC Life", "Música e Entretenimento", "Entretenimento", "https://d3dcdjv6dx07iz.cloudfront.net/v1/master/3722c60a815c199d9c0ef36c5b73da68a62b09d1/cc-eaaww2dyp3iih/RMC_Life_FR.m3u8", true, null, listOf(PLAYLIST_INT), listOf("RMC Life"), "Interactive"),
        Channel("suara-surabaya", "Suara Surabaya Visual Radio", "Música e Entretenimento", "Rádio visual", "https://ssfm.siar.us/ssfm/live/playlist.m3u8", true, null, listOf(PLAYLIST_INT), listOf("Suara Surabaya Visual Radio"), "Interactive"),

        // Família e Cultura / Documentary, Family, Travel, Cooking
        Channel("detetives-medicos", "Detetives Medicos", "Família e Cultura", "Documentários", "https://jmp2.uk/plu-638df93ae2f2a3000737c168.m3u8", true, null, listOf(PLAYLIST_BR), listOf("Detetives Medicos"), "Documentary"),
        Channel("history2-brazil", "History 2 Latin America Brazil", "Família e Cultura", "Documentários", "http://45.177.114.114/HISTORY_2/index.m3u8", true, null, listOf(PLAYLIST_BR), listOf("History 2 Latin America Brazil"), "Documentary"),
        Channel("history-brazil", "History (Brazil)", "Família e Cultura", "Documentários", "http://45.177.114.114/HISTORY/index.m3u8", true, null, listOf(PLAYLIST_BR), listOf("History Brazil"), "Documentary"),
        Channel("pluto-investigacao", "Pluto TV Investigacao", "Família e Cultura", "Documentários", "https://jmp2.uk/plu-5f32cf37c9ff2b00082adbc8.m3u8", true, null, listOf(PLAYLIST_BR), listOf("Pluto TV Investigacao"), "Documentary"),
        Channel("1kzn-tv", "1KZN TV", "Família e Cultura", "Família", "https://cdn.freevisiontv.co.za/sttv/smil:1kzn.stream.smil/playlist.m3u8", true, null, listOf(PLAYLIST_AFR), listOf("1KZN TV"), "Family"),
        Channel("active-family-hd", "Active Family HD", "Família e Cultura", "Família", "http://92.114.85.87:8010/play/a00p/index.m3u8", true, null, listOf(PLAYLIST_INT), listOf("Active Family HD"), "Family"),
        Channel("rakuten-family-movies", "Rakuten TV Family Movies", "Família e Cultura", "Filmes para família", "https://b0d59c8c98974e708e5ccb9a27cdfdfc.mediatailor.eu-west-1.amazonaws.com/v1/master/0547f18649bd788bec7b67b746e47670f558b6b2/production-LiveChannel-6205/master.m3u8", true, null, listOf(PLAYLIST_INT), listOf("Rakuten TV Family Movies"), "Family"),
        Channel("sts-hd", "STS HD", "Família e Cultura", "Família", "http://stream.mcquack.net/138/index.m3u8", true, null, listOf(PLAYLIST_INT), listOf("STS HD"), "Family"),
        Channel("tv-parana-turismo", "TV Parana Turismo", "Família e Cultura", "Viagens", "http://200.189.113.201/hls/tve.m3u8", true, null, listOf(PLAYLIST_BR), listOf("TV Parana Turismo"), "Travel"),
        Channel("pluto-kfood", "Pluto TV KFOOD", "Família e Cultura", "Culinária", "https://jmp2.uk/plu-633ee9ba83c08f00076b60a6.m3u8", true, null, listOf(PLAYLIST_BR), listOf("Pluto TV KFOOD"), "Cooking"),
        Channel("tastemade-brasil", "Tastemade Brasil", "Família e Cultura", "Culinária", "https://jmp2.uk/plu-5fd1419a3b4f4b000773ba85.m3u8", true, null, listOf(PLAYLIST_BR), listOf("Tastemade Brasil"), "Cooking")
    )

    fun byCategory(category: String): List<Channel> = channels.filter { it.category == category }
    fun bySubcategory(category: String, subcategory: String): List<Channel> = channels.filter { it.category == category && it.subcategory == subcategory }
}
