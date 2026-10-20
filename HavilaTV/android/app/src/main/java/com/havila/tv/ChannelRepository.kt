package com.havila.tv

/**
 * Canonical Havila TV catalogue built from the supplied channel list.
 * Each entry keeps the Havila category, Havila subcategory, channel name,
 * direct stream URL and logo URL supplied in the source file.
 */
object ChannelRepository {
    private fun c(
        id: String,
        name: String,
        category: String,
        subcategory: String,
        streamUrl: String,
        logoUrl: String? = null
    ) = Channel(
        id = id,
        name = name,
        category = category,
        description = subcategory,
        streamUrl = streamUrl,
        isLive = true,
        logoUrl = logoUrl,
        aliases = listOf(name),
        subcategory = subcategory
    )

    val channels = listOf(
        // NOTÍCIAS / NEWS
        c("24-horas-spain", "24 Horas (Spain)", "Notícias", "News", "https://ztnr.rtve.es/ztnr/1694255.m3u8", "https://i.ibb.co/21sXZ3GT/24h.png"),
        c("24-tv", "24 TV", "Notícias", "News", "https://turkmedya-live.ercdn.net/tv24/tv24.m3u8", "https://i.imgur.com/8FO41es.png"),
        c("30a-georgia-hollywood-review", "30A Georgia Hollywood Review", "Notícias", "News", "https://30a-tv.com/gh.m3u8", "https://images.axios.com/JiG3RuYBwpU_WZFeU6HHjt03FAU=/111x0:1191x1080/320x320/2023/11/09/1699558891265.jpg"),
        c("africa24-english", "Africa 24 English", "Notícias", "News", "https://edge20.vedge.infomaniak.com/livecast/ik:africa24english/manifest.m3u8", "https://africa24tv.com/wp-content/uploads/2023/12/logo-Africa24TVEnglish.png"),
        c("africanews-french", "Africanews French", "Notícias", "News", "https://cdn-euronews.akamaized.net/live/eds/africanews-fr/25050/index.m3u8", "https://i.imgur.com/5UxU4zc.png"),
        c("agromais", "AgroMais", "Notícias", "News", "http://45.162.64.114/AGROMAIS/index.m3u8", "https://i.imgur.com/sFcOZeo.png"),
        c("al-arabiya-english", "Al Arabiya English", "Notícias", "News", "https://live.alarabiya.net/alarabiapublish/english/playlist_dvr.m3u8", "https://shahid.mbc.net/mediaObject/7cfc0baf-1201-477f-b722-d3099fafe17f?height=230&width=512&croppingPoint=&version=1&type=png"),
        c("al-jazeera-english", "Al Jazeera English", "Notícias", "News", "https://live-hls-apps-aje-fa.getaj.net/AJE/index.m3u8", "https://i.imgur.com/7bRVpnu.png"),
        c("americas-voice", "America's Voice", "Notícias", "News", "https://content.uplynk.com/channel/26bd482ffe364a1282bc3df28bd3c21f.m3u8"),
        c("americas-voice-news", "America's Voice News", "Notícias", "News", "https://jmp2.uk/plu-5e1f7da4bc7d740009831259.m3u8", "https://i.imgur.com/C4w0eYC.png"),
        c("army-tv", "Army TV", "Notícias", "News", "https://api-tv.ipnet.ua/api/v1/manifest/2118742632.m3u8", "https://i.imgur.com/pGByjbh.png"),
        c("astra-tv", "Astra TV", "Notícias", "News", "https://server.gointernet.gr/live/livestream.m3u8", "https://i.imgur.com/oYRPfZm.png"),
        c("atacama-noticias", "Atacama Noticias", "Notícias", "News", "https://v2.tustreaming.cl/atacamanoticias/index.m3u8", "https://i.imgur.com/Avx4Ecf.png"),
        c("bbc-news-africa", "BBC News Africa", "Notícias", "News", "https://vs-cmaf-push-ww-live.akamaized.net/x=4/i=urn:bbc:pips:service:bbc_news_channel_hd/hevc_iptv_mse_v0.mpd", "https://xstreamcp-assets-msp.streamready.in/assets/LIVETV/LIVECHANNEL/LIVETV_LIVETVCHANNEL_BBC_NEWS/images/LOGO_HD/image.png"),
        c("bbc-news-north-america", "BBC News North America", "Notícias", "News", "http://23.237.104.106:8080/USA_BBC_NEWS/index.m3u8", "https://xstreamcp-assets-msp.streamready.in/assets/LIVETV/LIVECHANNEL/LIVETV_LIVETVCHANNEL_BBC_NEWS/images/LOGO_HD/image.png"),
        c("bbc-news-south-asia", "BBC News South Asia", "Notícias", "News", "https://mumbai-edge.smartplaytv.in/BBCNews/index.m3u8", "https://xstreamcp-assets-msp.streamready.in/assets/LIVETV/LIVECHANNEL/LIVETV_LIVETVCHANNEL_BBC_NEWS/images/LOGO_HD/image.png"),
        c("bfm-business", "BFM Business", "Notícias", "Business", "https://ncdn-live-bfm.pfd.sfr.net/shls/LIVE$BFM_BUSINESS/index.m3u8?end=END&start=LIVE", "https://i.imgur.com/psqIHn4.png"),
        c("cheddar-news", "Cheddar News", "Notícias", "News", "https://hls.livecdn.io/cheddar.com/cheddar/playlist.m3u8", "https://i.imgur.com/tuP9GW8.png"),
        c("cnn-international", "CNN International", "Notícias", "News", "https://d3bp6dwmpbdajl.cloudfront.net/v1/master/3722c60a815c199d9c0ef36c5b73da68a62b09d1/cc-ury0meh5m4nzm/index.m3u8", "https://xstreamcp-assets-msp.streamready.in/assets/LIVETV/LIVECHANNEL/LIVETV_LIVETVCHANNEL_CNN_INTERNATIONAL/images/LOGO_HD/image.png"),
        c("cnn-portugal", "CNN Portugal", "Notícias", "News", "https://github.com/thomraider12/canaistvpt/raw/main/m3u8s/cnnpt.m3u8", "https://i.imgur.com/bc6tYVZ.png"),
        c("de-ultimo-minuto-tv", "De Ultimo Minuto TV", "Notícias", "News", "https://soportedvb.click:3620/live/deultimominutomedialive.m3u8", "https://i.imgur.com/6ML2z17.png"),
        c("dw-english", "DW English", "Notícias", "News", "https://amg01644-amg01644c1-amgplt0343.playout.now3.amagi.tv/ts-eu-w1-n2/playlist/amg01644-amg01644c1-amgplt0343/playlist.m3u8", "https://i.imgur.com/8MRNFb9.png"),
        c("euronews-portuguese", "Euronews Portuguese", "Notícias", "News", "https://jmp2.uk/plu-619e6614c9d9650007a2b171.m3u8", "https://i.imgur.com/8t9mdg9.png"),
        c("euronews-english", "Euronews English HD", "Notícias", "News", "https://dash4.antik.sk/live/test_euronews/playlist.m3u8", "https://jiotvimages.cdn.jio.com/dare_images/images/Euro_News.png"),
        c("france24-english", "France 24 English", "Notícias", "News", "https://live.france24.com/hls/live/2037218-b/F24_EN_HI_HLS/master_5000.m3u8", "https://xstreamcp-assets-msp.streamready.in/assets/LIVETV/LIVECHANNEL/LIVETV_LIVETVCHANNEL_FRANCE_24/images/LOGO_HD/image.png"),
        c("miracle-channel", "Miracle Channel", "Notícias", "News", "https://live.miracleonline.org/hls/miracle1.m3u8", "https://i.imgur.com/yrt7Rw9.png"),
        c("momento24", "Momento24", "Notícias", "News", "https://backupmaxmedia.hvmultiplay.com/hls/stream2/momento24.m3u8", "https://i.imgur.com/kLy6w9e.jpeg"),
        c("nhk-world-japan", "NHK World-Japan", "Notícias", "News", "https://masterpl.hls.nhkworld.jp/hls/w/live/smarttv.m3u8", "https://jiotvimages.cdn.jio.com/dare_images/images/NHK_World_Japan.png"),
        c("noticias-16", "Noticias 16", "Notícias", "News", "https://edge.essastream.com/noticias16/playlist.m3u8"),
        c("noticias-rcn", "Noticias RCN", "Notícias", "News", "https://jmp2.uk/plu-67d9b0ebc290c9499046e88f.m3u8", "https://i.imgur.com/FMX4QCM.png"),
        c("noticiero-90-minutos", "Noticiero 90 Minutos", "Notícias", "News", "https://play.cdn.enetres.net/621B146D29C541AFB1507809F038F471021/021/playlist.m3u8", "https://i.imgur.com/ThaRRCN.png"),
        c("rtp-noticias", "RTP Noticias", "Notícias", "News", "https://streaming-live.rtp.pt/livetvhlsDVR/rtpnHDdvr.smil/playlist.m3u8", "https://cdn-images.rtp.pt/common/img/channels/logos/color/horizontal/rtpnoticias_2026.png"),

        // NOTÍCIAS / PUBLIC
        c("mntv", "MNTV", "Notícias", "Public", "https://www.khuwemultimedia.com/hls/mntv/mntv.m3u8", "https://mntv.co.mz/wp-content/uploads/2025/11/Prancheta-4_1-1.png"),
        c("reino-infantil-mz", "Reino Infantil", "Notícias", "Public", "https://www.khuwemultimedia.com/hls/reinoinfantiltv/reinoinfantiltv/playlist.m3u8", "https://i.imgur.com/oAygpDw.png"),
        c("tv-mana-mocambique", "TV Mana Mocambique", "Notícias", "Public", "https://w2.manasat.com/tvmana-mz/smil:tvmana-mz.smil/playlist.m3u8", "https://i.imgur.com/R5YrV5s.png"),
        c("tv-vitoria", "TV Vitoria", "Notícias", "Public", "https://stmv1.srvif.com/tvvitoriamz/tvvitoriamz/playlist.m3u8", "https://i.imgur.com/HlWzzfj.png"),
        c("tvm-mozambique", "TVM (Mozambique)", "Notícias", "Public", "http://196.28.226.121:1935/live/smil:Channel1.smil/playlist.m3u8", "https://i.imgur.com/Vqv7R3Y.png"),
        c("24-horas-public", "24 Horas", "Notícias", "Public", "https://ztnr.rtve.es/ztnr/1694255.m3u8", "https://i.ibb.co/21sXZ3GT/24h.png"),
        c("canal-sur-noticias", "Canal Sur Noticias", "Notícias", "Public", "https://cdnlive.codev8.net/rtvalive/smil:channel42.smil/playlist.m3u8", "https://i.imgur.com/A3JscwS.png"),
        c("tv-ufop", "TV UFOP", "Notícias", "Public", "https://app.tvufop.com.br/hls/tvufop.m3u8", "https://raw.githubusercontent.com/daniloroxette/tvufop/refs/heads/main/images/tvufop_logo.png"),
        c("uaa-tv", "UAA TV", "Notícias", "Public", "https://5fe2654d6127d.streamlock.net:443/uaa2/videouaa2/playlist.m3u8", "https://i.imgur.com/brCSiGh.png"),

        // NOTÍCIAS / LEGISLATIVE
        c("actv", "ACTV (United States)", "Notícias", "Legislative", "https://castus-vod-dev.s3.amazonaws.com/vod_clients/athens/live/ch2/video.m3u8", "https://i.imgur.com/Z6lB0Ww.png"),
        c("atxn2", "ATXN2", "Notícias", "Legislative", "https://cdn-atxn-01.vos360.video/Content/HLS/Live/channel(f97b5ddc-b7e4-dff8-9f10-8c16d0833639)/index.m3u8", "https://i.imgur.com/tSMtDlz.png"),
        c("batv-government", "BATV Government Channel", "Notícias", "Legislative", "https://livestream.telvue.com/billerica3/f7b44cfafd5c52223d5498196c8a2e7b.sdp/playlist.m3u8", "https://i.imgur.com/4MdSstx.png"),
        c("canal-congreso", "Canal Congreso", "Notícias", "Legislative", "http://138.121.15.230:9002/CANAL-CONGRESO/index.m3u8", "https://i.imgur.com/3VnL8dp.png"),
        c("canal-gov", "Canal Gov", "Notícias", "Legislative", "http://45.177.114.115/TV_BRASIL_2/index.m3u8", "https://i.imgur.com/rHPY0Yv.png"),
        c("tv-camara", "TV Camara (Brazil)", "Notícias", "Legislative", "https://stream3.camara.gov.br/tv1/manifest.m3u8", "https://i.imgur.com/Sdou1Ab.png"),
        c("yuma-channel-72", "Yuma Channel 72", "Notícias", "Legislative", "https://livestream.telvue.com/yumaaz1/f7b44cfafd5c52223d5498196c8a2e7b.sdp/playlist.m3u8", "https://i.imgur.com/QsFBCWv.png"),

        // NOTÍCIAS / BUSINESS
        c("100-news", "100% NEWS", "Notícias", "Business", "http://85.238.112.40:8810/hls_sec/239.33.16.32-.m3u8", "https://i.imgur.com/bSXKdK5.png"),
        c("apart-tv", "apart TV", "Notícias", "Business", "https://2-fss-2.streamhoster.com/pl_124/206820-4338160-1/playlist.m3u8", "https://i.imgur.com/IBcMIcZ.png"),
        c("bloomberg-originals", "Bloomberg Originals", "Notícias", "Business", "https://bloomberg.com/media-manifest/streams/qt.m3u8", "https://jiotvimages.cdn.jio.com/dare_images/images/Bloomberg_Originals.png"),
        c("bloomberg-tv-asia", "Bloomberg TV Asia Live Event", "Notícias", "Business", "https://bloomberg.com/media-manifest/streams/asia-event.m3u8", "https://i.imgur.com/OuogLHx.png"),
        c("cnbc-tv18-prime", "CNBC TV18 Prime HD", "Notícias", "Business", "https://n18syndication.akamaized.net/bpk-tv/CNBC_Tv18_Prime_HD_NW18_MOB/output01/index.m3u8", "https://jiotvimages.cdn.jio.com/dare_images/images/CNBCTV18Prime.png"),
        c("negocios-tv", "Negocios TV", "Notícias", "Business", "https://streaming013.gestec-video.com/hls/negociostv.m3u8", "https://m.media-amazon.com/images/I/21HEgSJn2gL.png"),

        // NOTÍCIAS / WEATHER
        c("accuweather-now", "AccuWeather NOW", "Notícias", "Weather", "https://cdn-ue1-prod.tsv2.amagi.tv/linear/amg00684-accuweather-accuweather-plex/playlist.m3u8", "https://i.imgur.com/M8wbVYK.png"),
        c("fox-weather", "Fox Weather", "Notícias", "Weather", "https://247wlive.foxweather.com/stream/index.m3u8", "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b9/Fox_Weather_logo.svg/960px-Fox_Weather_logo.svg.png"),
        c("sky-news-weather", "Sky News Weather", "Notícias", "Weather", "https://distro001-gb-hls1-prd.delivery.skycdp.com/easel_cdn/ngrp:weather_loop.stream_all/playlist.m3u8", "https://pbs.twimg.com/profile_images/1604994875459518464/lGt2wEqM_400x400.jpg"),
        c("weatherspy", "WeatherSpy (Australia)", "Notícias", "Weather", "https://jukin-weatherspy-2-au.samsung.wurl.tv/playlist.m3u8", "https://i.imgur.com/GTxN2nH.png"),

        // DESPORTO / SPORTS
        c("espn4-brazil", "ESPN 4 (Brazil)", "Desporto", "Sports", "http://181.78.197.59:8000/play/a07n/index.m3u8", "https://upload.wikimedia.org/wikipedia/commons/thumb/7/78/ESPN_4_logo.svg/960px-ESPN_4_logo.svg.png"),
        c("espn-brazil", "ESPN (Brazil)", "Desporto", "Sports", "http://181.78.197.59:8000/play/a07z/index.m3u8", "https://upload.wikimedia.org/wikipedia/commons/thumb/2/2f/ESPN_wordmark.svg/960px-ESPN_wordmark.svg.png"),
        c("fifa-plus-portuguese", "FIFA+ Portuguese", "Desporto", "Sports", "https://e3be9ac5.wurl.com/master/f36d25e7e52f1ba8d7e56eb859c636563214f541/TEctYnJfRklGQVBsdXNQb3J0dWd1ZXNlX0hMUw/playlist.m3u8", "https://upload.wikimedia.org/wikipedia/commons/thumb/9/9c/FIFA%2B_(2025).svg/960px-FIFA%2B_(2025).svg.png"),
        c("ge-fast", "ge Fast", "Desporto", "Sports", "https://amg00716-globo-amg00716c1-tcl-br-9495.playouts.now.amagi.tv/playlist.m3u8", "https://i.ibb.co/WGXHDnF/logo-ge-fast.png"),
        c("n-sports", "N Sports", "Desporto", "Sports", "https://ogc-nsprt-tcl-roku-syndication.otteravision.com/ogc/nsprt/nsprt.m3u8", "https://i.imgur.com/7QnJojr.png"),
        c("pluto-esportes", "Pluto TV Esportes", "Desporto", "Sports", "https://jmp2.uk/plu-5f32d2db0af67400077f29c4.m3u8", "https://images.pluto.tv/channels/5f32d2db0af67400077f29c4/colorLogoSVG_1780355759776.svg"),
        c("red-bull-tv-br", "Red Bull TV BR", "Desporto", "Sports", "https://d03ae6b5c6724c24867e97a3dc04934a.mediatailor.us-west-2.amazonaws.com/v1/master/ba62fe743df0fe93366eba3a257d792884136c7f/LINEAR-1026-WORBBRPTFAST-WHALETVPLUS/1026/hls/master/playlist.m3u8", "https://images.pluto.tv/channels/5e7cb84a172a0f0007da69e4/colorLogoPNG.png"),
        c("30a-golf-kingdom", "30A Golf Kingdom", "Desporto", "Sports", "https://30a-tv.com/feeds/vidaa/golf.m3u8", "https://i.imgur.com/Lv53nh4.png"),
        c("a-spor-sd", "A Spor SD", "Desporto", "Sports", "https://rnttwmjcin.turknet.ercdn.net/lcpmvefbyo/aspor/aspor.m3u8", "https://i.imgur.com/ZhkZzLf.png"),
        c("aci-sport-tv", "ACI Sport TV SD", "Desporto", "Sports", "https://webstream.multistream.it/memfs/e2cb3629-c1a2-495b-b43a-9eb386f04ed8.m3u8", "https://i.imgur.com/U8cHMOt.png"),
        c("africa24-sport", "Africa 24 Sport", "Desporto", "Sports", "https://africa24.vedge.infomaniak.com/livecast/ik:africa24sport/manifest.m3u8", "https://i0.wp.com/africa24tv.com/wp-content/uploads/2023/12/LOGO-AFRICASPORT-4-HD-sans-fond.png?fit=512%2C107&ssl=1"),
        c("as3-sport-tv", "AS3 Sport TV", "Desporto", "Sports", "https://streamtv.as3sport.online:3394/hybrid/play.m3u8", "https://i.ibb.co/bRmGbsyV/A3-SPORTTV.jpg"),
        c("bein-sports-xtra", "beIN SPORTS XTRA", "Desporto", "Sports", "https://bein-xtra-bein.amagi.tv/playlist.m3u8", "https://i.ibb.co/HT49GPmB/XTRA-2.png"),
        c("canal-plus-sport-2-sk", "Canal+ Sport 2 SK", "Desporto", "Sports", "http://rpprod.hbbtv.cdn.cra.cz:8080/hbbTV/cme-sk-canal-plus-sport2/live_fullhd?offsetSeconds=0", "https://static.wikia.nocookie.net/logopedia/images/a/ab/CANAL%2B_SPORT_2.svg/revision/latest?cb=20241028202739"),
        c("canal-plus-sport-5-sk", "Canal+ Sport 5 SK", "Desporto", "Sports", "http://rpprod.hbbtv.cdn.cra.cz:8080/hbbTV/cme-sk-canal-plus-sport5/live_fullhd?offsetSeconds=0", "https://static.wikia.nocookie.net/logopedia/images/3/39/CANAL%2B_SPORT_5.svg/revision/latest?cb=20241028202829"),
        c("cbs-sports-hq", "CBS Sports HQ", "Desporto", "Sports", "https://jmp2.uk/plu-5e9f2c05172a0f0007db4786.m3u8", "https://i.imgur.com/q8BENJg.png"),
        c("eleven-sports-2", "Eleven Sports 2", "Desporto", "Sports", "http://92.114.85.87:8002/play/a00f/index.m3u8", "https://i.imgur.com/bTIRgqG.png"),
        c("eleven-sports-4", "Eleven Sports 4", "Desporto", "Sports", "http://92.114.85.87:8002/play/a00h/index.m3u8", "https://i.imgur.com/SEKXaAW.png"),
        c("espn-us-hd", "ESPN (United States) HD", "Desporto", "Sports", "http://85.237.89.160:9590/usa-s/ESPN-HD/index.m3u8", "https://upload.wikimedia.org/wikipedia/commons/thumb/2/2f/ESPN_wordmark.svg/960px-ESPN_wordmark.svg.png"),
        c("eurosport", "Eurosport", "Desporto", "Sports", "https://drk6xq0vhn.gpcdn.net/live/euro_sports_hd_abr/index.m3u8", "https://dtil.tmsimg.com/assets/GNLZZGG002E9LQS.png?lock=720x540"),
        c("fifa-plus-us", "FIFA+ United States", "Desporto", "Sports", "https://d2w9q46ikgrcwx.cloudfront.net/v1/master/3722c60a815c199d9c0ef36c5b73da68a62b09d1/cc-of5cbk3sav3w5/v1/sysdata_s_p_a_fifa_7/samsungheadend_us/latest/main/hls/playlist.m3u8", "https://upload.wikimedia.org/wikipedia/commons/thumb/9/9c/FIFA%2B_(2025).svg/960px-FIFA%2B_(2025).svg.png"),
        c("fox-deportes", "Fox Deportes", "Desporto", "Sports", "http://23.237.104.106:8080/USA_FOX_DEPORTES/index.m3u8", "https://upload.wikimedia.org/wikipedia/commons/thumb/3/3b/FOX_Deportes_logo.png/960px-FOX_Deportes_logo.png"),
        c("fox-sports-1", "Fox Sports 1", "Desporto", "Sports", "http://85.237.89.160:9590/usa-s/FOX-SPORTS-1/index.m3u8", "https://i.imgur.com/O9BapV9.png"),
        c("fox-sports-news", "Fox Sports News", "Desporto", "Sports", "https://skynewsau-live-premium.akamaized.net/hls/live/2105307/foxsportsnews/live_1080p.m3u8", "https://i.imgur.com/FhMQrJk.png"),
        c("fuel-tv", "FUEL TV", "Desporto", "Sports", "https://amg01074-fueltv-fueltvemeaen-rakuten-b6j62.amagi.tv/hls/amagi_hls_data_rakutenAA-fueltvemeaen/CDN/master.m3u8", "https://i.imgur.com/I8mviBy.png"),
        c("fuel-tv-au", "FUEL TV AU", "Desporto", "Sports", "https://amg01074-fueltv-fueltvau-samsungau-g09kq.amagi.tv/playlist/amg01074-fueltv-fueltvau-samsungau/playlist.m3u8", "https://i.imgur.com/I8mviBy.png"),
        c("pluto-e-sports", "Pluto TV E-Sports", "Desporto", "Sports", "https://jmp2.uk/plu-5ff3934600d4c7000733ff49.m3u8", "https://images.pluto.tv/channels/5ff3934600d4c7000733ff49/colorLogoPNG.png"),
        c("red-bull-tv", "Red Bull TV", "Desporto", "Sports", "https://3ea22335.wurl.com/master/f36d25e7e52f1ba8d7e56eb859c636563214f541/UmFrdXRlblRWLWdiX1JlZEJ1bGxUVl9ITFM/playlist.m3u8", "https://images.pluto.tv/channels/5e7cb84a172a0f0007da69e4/colorLogoPNG.png"),
        c("sony-sports-ten-1", "Sony Sports Ten 1 HD", "Desporto", "Sports", "http://103.185.24.134:3001/SonyTenSports1/index.m3u8", "https://xstreamcp-assets-msp.streamready.in/assets/LIVETV/LIVECHANNEL/LIVETV_LIVETVCHANNEL_SONY_SPORTS_TEN_1/images/LOGO_HD/image.png"),
        c("viju-sport", "viju+ Sport", "Desporto", "Sports", "http://stream.mcquack.net/333/index.m3u8", "https://i.imgur.com/fXESG0l.png"),
        c("agrocanal", "AgroCanal", "Desporto", "Outdoor", "http://45.162.64.114/AGRO_CANAL/index.m3u8", "https://i.imgur.com/5XyopHf.png"),
        c("30a-darcizzle-offshore", "30A Darcizzle Offshore", "Desporto", "Auto", "https://30a-tv.com/darcizzle.m3u8", "https://i.imgur.com/Q4l2uao.png"),
        c("agricultura-tv", "Agricultura TV", "Desporto", "Auto", "https://redirector.rudo.video/hls-video/ey6283je82983je9823je8jowowiekldk9838274/921tv/921tv.smil/playlist.m3u8", "https://i.imgur.com/sxrYc3Q.png"),
        c("agro-tv-peru", "Agro TV (Peru)", "Desporto", "Auto", "https://live-evg8.tv360.bitel.com.pe/bitel/agroSRT/playlist.m3u8", "https://i.imgur.com/1uLGa3w.png"),
        c("agrotendencia-tv", "Agrotendencia TV", "Desporto", "Auto", "https://5fc584f3f19c9.streamlock.net/agrotendencia/videoagrotendencia_hls1/playlist.m3u8", "https://i.imgur.com/frd60hR.png"),
        c("akc-tv", "AKC TV", "Desporto", "Auto", "https://broadcast.blivenyc.com/speed/broadcast/22/desktop-playlist.m3u8", "https://i.imgur.com/XRTfoSp.png"),
        c("love-nature", "Love Nature", "Desporto", "Auto", "https://aegis-cloudfront-1.tubi.video/6d6d0f24-8445-4b4c-bdf6-44f9e38beaa4/playlist.m3u8", "https://tv.lovenature.com/wp-content/uploads/2024/10/LoveNature2024_Logo_full_colour-copy.png"),
        c("italian-fishing-tv", "Italian Fishing TV", "Desporto", "Auto", "https://fms.premio.link/fishingtv/playlist.m3u8", "https://i.imgur.com/qrn6tUO.png"),
        c("waterbear", "WaterBear", "Desporto", "Outdoor", "https://amg01415-waterbearnetwor-waterbear-samsunguk-1h0y8.amagi.tv/playlist/amg01415-waterbearnetwor-waterbear-samsunguk/playlist.m3u8", "https://framerusercontent.com/images/41VQQ7F0fYlp1MX1N4Ixog5QnPI.png"),

        // GOSPEL / RELIGIOUS
        c("angel-tv-portuguese", "Angel TV Portuguese", "Gospel", "Religious", "https://janya-digimix.akamaized.net/vglive-sk-382409/portuese/ngrp:angelportuguese_all/playlist.m3u8", "https://i.imgur.com/qKLEGU7.png"),
        c("adb-tv", "ADB TV", "Gospel", "Religious", "https://live-tv.waytv.pt/adbtv/adbtv/playlist.m3u8", "https://i.ibb.co/HGfTJwt/ADBTV.jpg"),
        c("fonte-tv", "Fonte TV", "Gospel", "Religious", "https://59fb88a1b1108.streamlock.net/fonte/_definst_/fontetv/chunklist_w1224511652.m3u8", "https://i.imgur.com/7q2BmNc.png"),
        c("gospel-cartoon", "Gospel Cartoon", "Gospel", "Religious", "https://stmv1.srvif.com/gospelcartoon/gospelcartoon/playlist.m3u8", "https://i.imgur.com/yxjPno5.png"),
        c("gospel-movie-tv", "Gospel Movie TV", "Gospel", "Religious", "https://stmv1.srvif.com/gospelf/gospelf/playlist.m3u8", "https://i.imgur.com/cQN3nWt.png"),
        c("impd-tv-worldwide", "IMPD TV Worldwide", "Gospel", "Religious", "https://igrejamundial.nuvemplay.live/hls/stream.m3u8", "https://i.imgur.com/9dW3AUQ.png"),
        c("kk-tv", "KK TV", "Gospel", "Religious", "https://w1.manasat.com/ktv-angola/smil:ktv-angola.smil/playlist.m3u8", "https://i.imgur.com/jWOB0oU.png"),
        c("kuriakos-tv", "Kuriakos TV", "Gospel", "Religious", "https://w1.manasat.com/ktv/smil:ktv.smil/playlist.m3u8", "https://i.imgur.com/xdAW0D5.png"),
        c("novo-tempo", "Novo Tempo", "Gospel", "Religious", "http://45.162.64.114/NOVO_TEMPO/index.m3u8"),
        c("plenitude-tv", "Plenitude TV", "Gospel", "Religious", "https://strv.painelvox.top/plenitude/plenitude/playlist.m3u8"),
        c("rede-gospel", "Rede Gospel", "Gospel", "Religious", "https://cdn.live.br1.jmvstream.com/w/LVW-8719/LVW8719_AcLVAxWy5J/playlist.m3u8", "https://i.imgur.com/mttSwgO.png"),
        c("rit-tv", "RIT TV", "Gospel", "Religious", "https://acesso.ecast.site:3648/live/ritlive.m3u8", "https://i.imgur.com/Unjs1wi.png"),
        c("sbn-tv-global", "SBN TV Global", "Gospel", "Religious", "https://watch.sardius.media/channels/Ea867F3e1F56B06/site_8D363D13Eb/live.m3u8", "https://i.imgur.com/NJu6oYB.png"),
        c("tv-mana-1", "TV Mana 1", "Gospel", "Religious", "https://w1.manasat.com/tvmana-1/smil:tvmana-1.smil/playlist.m3u8", "https://i.imgur.com/6jNqpTP.png"),
        c("3abn-proclaim", "3ABN Proclaim! Network", "Gospel", "Religious", "https://3abn.bozztv.com/3abn2/Pro_live/smil:Pro_live.smil/playlist.m3u8", "https://i.imgur.com/zUMUNhe.png"),
        c("alcance-tv", "Alcance TV (Mexico)", "Gospel", "Religious", "https://5bf8041cb3fed.streamlock.net/AlcanceTV/AlcanceTV/playlist.m3u8", "https://i.imgur.com/5nYjRlb.png"),
        c("angel-tv-africa", "Angel TV Africa", "Gospel", "Religious", "https://janya-digimix.akamaized.net/vglive-sk-904559/africa/ngrp:angelafrica_all/playlist.m3u8", "https://i.imgur.com/qKLEGU7.png"),
        c("ark-tv", "Ark TV", "Gospel", "Religious", "https://stream.hydeinnovations.com/arktv-international/index.fmp4.m3u8", "https://i.imgur.com/yCHNZXD.png"),
        c("benie-tv", "Benie TV", "Gospel", "Religious", "https://tv.voozmedia.fun/benietv/index.m3u8", "https://i.imgur.com/7FXi05e.jpeg"),
        c("btn-tv", "BTN TV (South Korea)", "Gospel", "Religious", "https://btn.nowcdn.co.kr/btn/btnlive2m/playlist.m3u8", "https://i.imgur.com/sOoS7RQ.png"),
        c("bethel-tv", "Bethel TV", "Gospel", "Religious", "https://alfa.betheltv.tv/srt/3_abr/playlist.m3u8", "https://i.imgur.com/zCVh765.png"),
        c("chosen-tv-english", "Chosen TV English", "Gospel", "Religious", "https://viewmedia7219.bozztv.com/wmedia/viewmedia100/web_024/Stream/playlist.m3u8"),
        c("coza-tv", "Coza TV", "Gospel", "Religious", "https://viewmedia7219.bozztv.com/wmedia/viewmedia100/web_019/Stream/playlist.m3u8", "https://i.imgur.com/QVBdpuF.png"),
        c("daystar-tv", "Daystar TV", "Gospel", "Religious", "https://live-mcl.cdn01.net/smarttv/48vy43564/playlist.m3u8", "https://i.imgur.com/uVdKHMG.png"),
        c("ebenezer-tv", "Ebenezer TV", "Gospel", "Religious", "https://5e85d90130e77.streamlock.net/6010/ngrp:6010_all/playlist.m3u8", "https://i.imgur.com/IC30j61.png"),
        c("elim-tv", "Elim TV", "Gospel", "Religious", "https://stream.soporteccrtv.xyz/live/elimtv/main_stream.m3u8", "https://i.imgur.com/payIg0N.png"),
        c("faith-africa", "Faith Africa", "Gospel", "Religious", "https://faithnow-hls.secdn.net/faithnow-holland-channel/play/FaithAfrica.smil/playlist.m3u8", "https://i.imgur.com/yAZE7aU.png"),
        c("faith-world-tv", "Faith World TV", "Gospel", "Religious", "https://faithworldtv.abs.tv/1826926230/index.m3u8", "https://i.imgur.com/RLHRjQ3.png"),
        c("fortaleza-tv", "Fortaleza TV", "Gospel", "Religious", "https://multimedia.fortalezatv.cl/video/video.m3u8", "https://i.imgur.com/BJpop5n.png"),
        c("gnn-tv", "GNN TV", "Gospel", "Religious", "https://na.linear.zype.com/bf94f9f4-383c-40e0-9d49-279a4f33ab8d/f29b8836-d990-4c8d-b421-192a8f43edc4-zype/live.m3u8", "https://i.imgur.com/itetPKw.png"),
        c("god-stands-tv-english", "God Stands TV English", "Gospel", "Religious", "https://online.godstands.tv:5443/WebRTCApp/streams/EnglishStreaming.m3u8", "https://i.imgur.com/EO6cYUI.png"),
        c("goodnews-tv", "GoodNews TV", "Gospel", "Religious", "https://bpgdlwwar3ze-hls-live.wmncdn.net/goodnews/live.stream/playlist.m3u8", "https://xstreamcp-assets-msp.streamready.in/assets/LIVETV/LIVECHANNEL/LIVETV_LIVETVCHANNEL_GOODNEWS_TV/images/LOGO_HD/image.png"),
        c("gospel-truth-tv", "Gospel Truth TV", "Gospel", "Religious", "https://na.linear.zype.com/bf94f9f4-383c-40e0-9d49-279a4f33ab8d/f29b8836-d990-4c8d-b421-192a8f43edc4-zype/manifest.m3u8", "https://i.imgur.com/LI52X2q.png"),
        c("greater-love-tv", "Greater Love TV", "Gospel", "Religious", "https://rpn.bozztv.com/dvrfl04/itv04019/index.m3u8", "https://i.imgur.com/WE0cRXR.png"),
        c("hope-channel-international", "Hope Channel International", "Gospel", "Religious", "https://jstre.am/live/jsl:cZXINbwrFj6.m3u8", "https://i.imgur.com/PNihgl2.png"),
        c("impact-channel", "Impact Channel", "Gospel", "Religious", "https://5fe2654d6127d.streamlock.net/impactchannel/videoimpactchannel/playlist.m3u8", "https://i.imgur.com/YRRID7W.png"),
        c("its-supernatural", "It's Supernatural! Network", "Gospel", "Religious", "https://content.uplynk.com/channel/b880a791bf95426d917a509e0c44796a.m3u8", "https://i.imgur.com/JWrdL9a.png"),
        c("it-is-written", "It Is Written TV", "Gospel", "Religious", "https://itiswrittentv.akamaized.net/xR8gyATuHMVx69u9G79DXnpNRL/playlist.m3u8", "https://i.imgur.com/0jgzoSF.png"),
        c("jewish-life-tv", "Jewish Life Television", "Gospel", "Religious", "https://jlt-jltv-strimm.otteravision.com/jlt/jltv/jltv.m3u8", "https://yt3.googleusercontent.com/i6ETvgNzA8vvGCo-Ih6eo2XZUwz2VVMnstLyNZVXBlwa4uOeUIEl-3tzphB8phJaibe-FPQ5Fw=s512-c-k-c0x00ffffff-no-rj"),
        c("loveworldsat", "LoveworldSAT", "Gospel", "Religious", "https://cdnstack.internetmultimediaonline.org/lwsat/lwsat.stream/index.m3u8", "https://i.imgur.com/Fg65XCS.png"),
        c("mcn-television", "MCN Television", "Gospel", "Religious", "https://eu1.servers10.com:8081/8338/index.m3u8", "https://i.imgur.com/SNUaCCw.png"),
        c("miracle-tv", "Miracle TV", "Gospel", "Religious", "https://viewmedia7219.bozztv.com/wmedia/viewmedia100/web_030/Stream/playlist.m3u8", "https://i.imgur.com/pTn8Vc3.png"),
        c("peniel-tv-biblia-abierta", "Peniel TV Biblia Abierta", "Gospel", "Religious", "https://s.emisoras.tv:8081/penielbibliaabierta/index.m3u8", "https://i.ibb.co/j3gGwYk/D64575438be2a3a54fdf64d4035ade48.png"),
        c("salvation-tv-india", "Salvation TV (India)", "Gospel", "Religious", "https://ktismaservers.in:3902/live/salvationtvlive.m3u8", "https://content.jdmagicbox.com/comp/bangalore/l5/080pxx80.xx80.150207120517.e4l5/catalogue/salvation-tv-network-pvt-m-g-road-bangalore-religious-organizations-1sabn2d.jpg"),
        c("sbn-tv-international", "SBN TV International", "Gospel", "Religious", "https://watch.sardius.media/channels/Ea867F3e1F56B06/site_09a4778E89/live.m3u8", "https://i.imgur.com/NJu6oYB.png"),
        c("thedove-tv", "theDove TV", "Gospel", "Religious", "https://thedovetv.bozztv.com/thedovetv/thedovetv/index.m3u8", "https://i.imgur.com/ULtuRsj.png"),
        c("tv-mana-1", "TV Mana 1", "Gospel", "Religious", "https://w1.manasat.com/tvmana-1/smil:tvmana-1.smil/playlist.m3u8", "https://i.imgur.com/6jNqpTP.png"),
        c("tv-mana-brasil", "TV Mana Brasil", "Gospel", "Religious", "https://w1.manasat.com/tvmana-brasil/smil:tvmana-brasil.smil/playlist.m3u8", "https://i.imgur.com/AFb712e.png"),
        c("tv-mana-mocambique-gospel", "TV Mana Mocambique", "Gospel", "Religious", "https://w2.manasat.com/tvmana-mz/smil:tvmana-mz.smil/playlist.m3u8", "https://i.imgur.com/R5YrV5s.png"),
        c("acnn", "ACNN", "Gospel", "Religious", "https://viewmedia7219.bozztv.com/wmedia/viewmedia100/web_014/Stream/playlist.m3u8", "https://i.imgur.com/91JLpxr.png"),
        c("salvation-tv-nigeria", "Salvation TV (Nigeria)", "Gospel", "Religious", "https://viewmedia7219.bozztv.com/wmedia/viewmedia100/web_017/Stream/playlist.m3u8", "https://i.imgur.com/Nb08dfK.png"),
        c("yadah-tv", "Yadah TV", "Gospel", "Religious", "https://viewmedia7219.bozztv.com/wmedia/viewmedia100/web_018/Stream/playlist.m3u8", "https://i.imgur.com/4BNVhoC.png"),
        c("superscreen-tv", "Superscreen TV", "Gospel", "Religious", "https://video1.getstreamhosting.com:1936/8398/8398/playlist.m3u8", "https://i.imgur.com/uTNmSWE.png"),

        // FILMES E SÉRIES / MOVIES
        c("gospel-movie-tv-movies", "Gospel Movie TV", "Filmes e Séries", "Movies", "https://stmv1.srvif.com/gospelf/gospelf/playlist.m3u8", "https://i.imgur.com/cQN3nWt.png"),
        c("itage-tv", "Itage TV", "Filmes e Séries", "Movies", "https://viewmedia7219.bozztv.com/wmedia/viewmedia100/web_011/Stream/playlist.m3u8", "https://i.imgur.com/FP1aUPw.jpg"),
        c("axn-movies-portugal", "AXN Movies (Portugal)", "Filmes e Séries", "Movies", "http://50.7.120.34:8080/AXN_BLACK/index.m3u8", "https://i.imgur.com/tJQckal.png"),
        c("axn-brazil", "AXN (Brazil)", "Filmes e Séries", "Movies", "http://170.83.16.50/AXN/index.m3u8", "https://upload.wikimedia.org/wikipedia/commons/thumb/5/52/AXN_logo_%282015%29.svg/960px-AXN_logo_%282015%29.svg.png"),
        c("filmes-suspense", "Filmes Suspense", "Filmes e Séries", "Movies", "https://jmp2.uk/plu-5f171d3442a0500007362f22.m3u8", "https://images.pluto.tv/channels/5f171d3442a0500007362f22/colorLogoPNG.png"),
        c("mytime-movie-network", "MyTime Movie Network (Brazil)", "Filmes e Séries", "Movies", "https://appletree-mytime-samsungbrazil.amagi.tv/playlist.m3u8", "https://i.imgur.com/aiGQtzI.png"),
        c("kuriakos-cine", "Kuriakos Cine", "Filmes e Séries", "Movies", "https://w2.manasat.com/kcine/smil:kcine.smil/playlist.m3u8", "https://i.imgur.com/CZViCwB.jpg"),
        c("sony-movies", "Sony Movies", "Filmes e Séries", "Movies", "http://45.162.64.114/SONY_MOVIES/index.m3u8", "https://upload.wikimedia.org/wikipedia/commons/thumb/9/9b/Sony_Movies_Logo.svg/500px-Sony_Movies_Logo.svg.png"),
        c("telecine-action", "Telecine Action", "Filmes e Séries", "Movies", "http://46.151.196.223:14326/", "https://i.imgur.com/BHYWG0h.png"),
        c("pluto-ficcao-cientifica", "Pluto TV Ficcao Cientifica", "Filmes e Séries", "Movies", "https://jmp2.uk/plu-5fa15ad6367e170007cdd098.m3u8", "https://images.pluto.tv/channels/5fa15ad6367e170007cdd098/colorLogoPNG.png"),

        // FILMES E SÉRIES / SERIES
        c("awtv", "AWTV", "Filmes e Séries", "Series", "https://awtv.nuvemplay.live/hls/stream.m3u8", "https://i.imgur.com/2tdMyef.png"),
        c("macgyver", "MacGyver (United States)", "Filmes e Séries", "Series", "https://jmp2.uk/plu-63eb9dc84e83e70008abea92.m3u8", "https://images.pluto.tv/channels/63eb9dc84e83e70008abea92/colorLogoPNG.png"),
        c("pluto-desenhos-classicos", "Pluto TV Desenhos Classicos", "Filmes e Séries", "Series", "https://jmp2.uk/plu-655e5c4d2c46f3000877a54b.m3u8", "https://images.pluto.tv/channels/655e5c4d2c46f3000877a54b/colorLogoPNG.png"),

        // FILMES E SÉRIES / CLASSIC
        c("comedy-central-brasil", "Comedy Central Brasil", "Filmes e Séries", "Classic", "https://stmv2.srvif.com/gafeab/gafeab/playlist.m3u8", "https://comcastadvertising.com/wp-content/uploads/2021/10/Comedy-Central-White.png"),
        c("comedy-central-pluto", "Comedy Central Pluto TV BR", "Filmes e Séries", "Classic", "https://jmp2.uk/plu-5f357e91b18f0b00073583d2.m3u8", "https://images.pluto.tv/channels/5f357e91b18f0b00073583d2/colorLogoPNG.png"),
        c("failarmy-brazil", "FailArmy Brazil", "Filmes e Séries", "Classic", "https://jmp2.uk/plu-5f5141c1605ddf000748eb1b.m3u8", "https://i.imgur.com/VIpQJxL.png"),
        c("fora-tedio-tv", "Fora Tedio TV", "Filmes e Séries", "Classic", "https://stmv2.srvif.com/foratedio/foratedio/playlist.m3u8", "https://i.imgur.com/R9ul4T1.png"),
        c("pluto-series-comedia", "Pluto TV Series Comedia", "Filmes e Séries", "Classic", "https://jmp2.uk/plu-655e5bc94261ca000810cb17.m3u8", "https://images.pluto.tv/channels/655e5bc94261ca000810cb17/colorLogoPNG.png"),

        // HAVILA KIDS / KIDS
        c("gospel-cartoon-kids", "Gospel Cartoon", "Havila Kids", "Kids", "https://stmv1.srvif.com/gospelcartoon/gospelcartoon/playlist.m3u8", "https://i.imgur.com/yxjPno5.png"),
        c("3abn-kids", "3ABN Kids", "Havila Kids", "Kids", "https://3abn.bozztv.com/3abn2/Kids_live/smil:Kids_live.smil/playlist.m3u8", "https://i.imgur.com/z3npqO1.png"),
        c("god-stands-kids-club", "God Stands Kids Club TV", "Havila Kids", "Kids", "https://online.godstands.tv:5443/WebRTCApp/streams/kidsenglish.m3u8", "https://i.imgur.com/FxDBbpz.jpeg"),
        c("kuriakos-kids", "Kuriakos Kids", "Havila Kids", "Kids", "https://w2.manasat.com/kkids/smil:kkids.smil/playlist.m3u8", "https://i.imgur.com/SRX6EPY.png"),
        c("logos-tv-kids", "Logos TV Kids", "Havila Kids", "Kids", "https://streamer1.streamhost.org/salive/logoskidsH/playlist.m3u8", "https://i.imgur.com/Kr3U3Ub.png"),
        c("ebs-kids", "EBS Kids", "Havila Kids", "Kids", "https://ebsonair.ebs.co.kr/ebsufamilypc/familypc1m/playlist.m3u8", "https://i.imgur.com/62oo8Bx.png"),
        c("90s-kids", "90's Kids", "Havila Kids", "Kids", "https://jmp2.uk/plu-6452c814939a590008567a3b.m3u8", "https://images.pluto.tv/channels/6452c814939a590008567a3b/colorLogoPNG_1731836283044.png"),
        c("brat-tv", "Brat TV", "Havila Kids", "Kids", "https://streams2.sofast.tv/v1/master/611d79b11b77e2f571934fd80ca1413453772ac7/04072b68-dc6a-4d5e-98af-f356ba8d5063/playlist.m3u8", "https://i.imgur.com/Kkcp9Bk.png"),
        c("cartoon-network-latam", "Cartoon Network Latin America Brazil", "Havila Kids", "Animation", "http://15.204.246.24:8080/CartoonNetworkHD/index.m3u8", "https://upload.wikimedia.org/wikipedia/commons/thumb/8/80/Cartoon_Network_2010_logo.svg/960px-Cartoon_Network_2010_logo.svg.png"),
        c("cctv-14", "CCTV-14 HD", "Havila Kids", "Kids", "http://74.91.26.218:82/live/cctv14hd.m3u8", "https://i.imgur.com/c5SXW7i.png"),
        c("christian-youth-channel-cyc", "Christian Youth Channel CYC", "Havila Kids", "Kids", "http://media3.smc-host.com:1935/cycnow.com/smil:cyc.smil/playlist.m3u8", "https://i.imgur.com/61eV77C.png"),
        c("deparinti-ro", "DeParinti.ro", "Havila Kids", "Kids", "https://live77cdn2.antenaplay.ro/live2_fast/ch4/stream.m3u8", "https://assets.antenaplay.ro/2023/11/21/EO57SfnF8caVPEaQFs4Z.png"),
        c("dios-te-ve-kids", "Dios Te Ve Kids", "Havila Kids", "Kids", "https://s.emisoras.tv:8081/diostevekids/index.m3u8", "https://i.imgur.com/GnZicVH.png"),
        c("discovery-kids-latam", "Discovery Kids Latin America", "Havila Kids", "Kids", "http://181.224.200.5:2277/play/a00o/index.m3u8", "https://thumb.wikimedia.org/wikipedia/commons/thumb/7/7f/Discovery_Kids_2021_2D_sin_wordmark.svg/960px-Discovery_Kids_2021_2D_sin_wordmark.svg.png"),
        c("o-reino-infantil", "O Reino Infantil", "Havila Kids", "Kids", "https://jmp2.uk/plu-5f5c216df68f920007888315.m3u8", "https://i.imgur.com/qoVxc13.png"),
        c("turma-da-monica", "Turma da Monica", "Havila Kids", "Kids", "https://jmp2.uk/plu-5f997e44949bc70007a6941e.m3u8", "https://i.imgur.com/nrvPPrW.png"),
        c("pluto-tv-anime-br", "Pluto TV Anime BR", "Havila Kids", "Animation", "https://jmp2.uk/plu-5f12136385bccc00070142ed.m3u8", "https://images.pluto.tv/channels/5f12136385bccc00070142ed/colorLogoPNG_1777595293242.png"),
        c("naruto-brazil", "Naruto Brazil", "Havila Kids", "Animation", "https://jmp2.uk/plu-5f6df5a173d7340007c559f7.m3u8", "https://i.imgur.com/M8X3Kvc.png"),
        c("discovery-kids-latam-animation", "Discovery Kids Latin America Brazil", "Havila Kids", "Animation", "http://15.204.246.24:8080/DiscoveryKidsHD/index.m3u8", "https://thumb.wikimedia.org/wikipedia/commons/thumb/7/7f/Discovery_Kids_2021_2D_sin_wordmark.svg/960px-Discovery_Kids_2021_2D_sin_wordmark.svg.png"),

        // MÚSICA E ENTRETENIMENTO / MUSIC
        c("3abn-praise-him", "3ABN Praise Him Music Network", "Música e Entretenimento", "Music", "https://3abn.bozztv.com/3abn1/PraiseHim/smil:PraiseHim.smil/playlist.m3u8", "https://i.imgur.com/iBcqT8L.png"),
        c("cbn-gospel", "CBN Gospel", "Música e Entretenimento", "Music", "https://59d39900ebfb8.streamlock.net/ccbn/ccbn/playlist.m3u8", "https://i.imgur.com/SHBAblV.png"),
        c("harmony-gospel-music-tv", "Harmony Gospel Music TV", "Música e Entretenimento", "Music", "https://tgn.bozztv.com/watc57/watc57-harmony/watc57-harmony/index.m3u8", "https://i.imgur.com/iDiGBE3.png"),
        c("peniel-musical", "Peniel Musical", "Música e Entretenimento", "Music", "https://s.emisoras.tv:8081/penielmusical/index.m3u8", "https://i.imgur.com/IFQbGEf.png"),
        c("spirit-tv", "Spirit TV", "Música e Entretenimento", "Music", "https://cdnlive.myspirit.tv/LS-ATL-43240-2/index.m3u8", "https://i.imgur.com/yCp6vXz.png"),
        c("trace-gospel", "Trace Gospel", "Música e Entretenimento", "Music", "https://channels.trace.plus/Traceprod/GOSPEL_FR_hd/index.m3u8", "https://i.imgur.com/X0UmU3K.png"),
        c("trace-gospel-nigeria-east-africa", "Trace Gospel Nigeria and East Africa", "Música e Entretenimento", "Music", "https://channels.trace.plus/Traceprod/GOSPEL_ROA_hd/index.m3u8", "https://i.imgur.com/X0UmU3K.png"),
        c("trace-gospel-southern-africa", "Trace Gospel Southern Africa", "Música e Entretenimento", "Music", "https://channels.trace.plus/Traceprod/GOSPEL_SA_hd/index.m3u8", "https://i.imgur.com/X0UmU3K.png"),
        c("muzangala-tv", "Muzangala TV", "Música e Entretenimento", "Music", "https://5cf4a2c2512a2.streamlock.net/tvmuzangala/tvmuzangala/playlist.m3u8", "https://i.imgur.com/fBeaJoS.png"),

        // MÚSICA E ENTRETENIMENTO / ENTERTAINMENT
        c("hyundai-home-shopping", "Hyundai Home Shopping + Shop", "Música e Entretenimento", "Entertainment", "https://dtvstreaming.hyundaihmall.com/newcjp3/_definst_/newcjpstream.smil/playlist.m3u8", "https://i.imgur.com/DoNOuZE.png"),
        c("home-shopping", "Home & Shopping", "Música e Entretenimento", "Entertainment", "https://s28.qtcdn.co.kr/media/liveM3U8/idx/518045142/enc/1860408350/playlist.m3u8", "https://i.imgur.com/IcexRXG.png"),
        c("shopping-nt", "Shopping NT", "Música e Entretenimento", "Entertainment", "https://live.shoppingntmall.com/live/10011.m3u8", "https://i.imgur.com/BgbJp5X.png"),

        // MÚSICA E ENTRETENIMENTO / INTERACTIVE
        c("rmc-life", "RMC Life", "Música e Entretenimento", "Interactive", "https://d3dcdjv6dx07iz.cloudfront.net/v1/master/3722c60a815c199d9c0ef36c5b73da68a62b09d1/cc-eaaww2dyp3iih/RMC_Life_FR.m3u8", "https://img1.dc2.oqee.net/channel_pictures/3606/w512"),
        c("suara-surabaya", "Suara Surabaya Visual Radio", "Música e Entretenimento", "Interactive", "https://ssfm.siar.us/ssfm/live/playlist.m3u8", "https://upload.wikimedia.org/wikipedia/id/f/f8/Suara_Surabaya.svg"),

        // FAMÍLIA E CULTURA / DOCUMENTARY
        c("detetives-medicos", "Detetives Medicos", "Família e Cultura", "Documentary", "https://jmp2.uk/plu-638df93ae2f2a3000737c168.m3u8", "https://images.pluto.tv/channels/638df93ae2f2a3000737c168/colorLogoPNG.png"),
        c("history2-latin-america-brazil", "History 2 Latin America Brazil", "Família e Cultura", "Documentary", "http://45.177.114.114/HISTORY_2/index.m3u8", "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c9/History2_logo_%282022%29.svg/500px-History2_logo_%282022%29.svg.png"),
        c("history-brazil", "History (Brazil)", "Família e Cultura", "Documentary", "http://45.177.114.114/HISTORY/index.m3u8", "https://upload.wikimedia.org/wikipedia/commons/thumb/0/01/History_%282021%29.svg/500px-History_%282021%29.svg.png"),
        c("pluto-tv-investigacao", "Pluto TV Investigacao", "Família e Cultura", "Documentary", "https://jmp2.uk/plu-5f32cf37c9ff2b00082adbc8.m3u8", "https://i.imgur.com/JlQq0YJ.png"),

        // FAMÍLIA E CULTURA / FAMILY
        c("1kzn-tv", "1KZN TV", "Família e Cultura", "Family", "https://cdn.freevisiontv.co.za/sttv/smil:1kzn.stream.smil/playlist.m3u8", "https://admango.cdn.mangomolo.com/analytics/uploads/188/6544bebaae.jpg"),
        c("active-family-hd", "Active Family HD", "Família e Cultura", "Family", "http://92.114.85.87:8010/play/a00p/index.m3u8", "https://i.imgur.com/I1wdWAo.png"),
        c("rakuten-tv-family-movies", "Rakuten TV Family Movies", "Família e Cultura", "Family", "https://b0d59c8c98974e708e5ccb9a27cdfdfc.mediatailor.eu-west-1.amazonaws.com/v1/master/0547f18649bd788bec7b67b746e47670f558b6b2/production-LiveChannel-6205/master.m3u8", "https://i.imgur.com/Meew6eX.png"),
        c("sts-hd", "STS HD", "Família e Cultura", "Family", "http://stream.mcquack.net/138/index.m3u8", "https://i.imgur.com/ilUzAeb.png"),

        // FAMÍLIA E CULTURA / TRAVEL
        c("tv-parana-turismo", "TV Parana Turismo", "Família e Cultura", "Travel", "http://200.189.113.201/hls/tve.m3u8", "https://i.imgur.com/I6pKoBd.png"),

        // FAMÍLIA E CULTURA / COOKING
        c("pluto-tv-kfood", "Pluto TV KFOOD", "Família e Cultura", "Cooking", "https://jmp2.uk/plu-633ee9ba83c08f00076b60a6.m3u8", "https://images.pluto.tv/channels/633ee9ba83c08f00076b60a6/colorLogoPNG.png"),
        c("tastemade-brasil", "Tastemade Brasil", "Família e Cultura", "Cooking", "https://jmp2.uk/plu-5fd1419a3b4f4b000773ba85.m3u8", "https://i.imgur.com/FPy4zF4.png"),

        // GENERAL
        c("tv-zimbo", "TV Zimbo", "Notícias", "General", "https://sgn-cdn-video.vods2africa.com/Tv-Zimbo/index.fmp4.m3u8", "https://i.imgur.com/SFD8CBh.png")
    )

    fun byCategory(category: String): List<Channel> = channels.filter { it.category == category }
    fun bySubcategory(category: String, subcategory: String): List<Channel> = channels.filter { it.category == category && it.subcategory == subcategory }
}
