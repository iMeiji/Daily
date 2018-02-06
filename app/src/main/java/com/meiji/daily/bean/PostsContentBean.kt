package com.meiji.daily.bean

/**
 * Created by Meiji on 2016/11/23.
 */

class PostsContentBean {

    /**
     * isTitleImageFullScreen : false
     * rating : none
     * titleImage : https://pic3.zhimg.com/v2-66f227a03683cd9f37b35892ab2f34a2_r.png
     * links : {"comments":"/api/posts/23480182/comments"}
     * reviewers : []
     * topics : [{"url":"https://www.zhihu.com/topic/19568367","id":"19568367","name":"HTTPS"}]
     * titleImageSize : {"width":0,"height":0}
     * href : /api/posts/23480182
     * excerptTitle :
     * author : {"bio":"「小道消息」，微信号：WebNotes","hash":"99953853cc4219fabe8327301058357c","uid":26676083818496,"isOrg":false,"slug":"fenng","description":"手艺人.\n知乎74号用户.\n微信公众帐号「小道消息」, 微信号: 「WebNotes」\n\n黑我的人最后都变成了我的粉，需要新的黑子。","name":"Fenng","profileUrl":"https://www.zhihu.com/people/fenng","avatar":{"id":"9ee82f3f07623303aa42164b523ac267","template":"https://pic4.zhimg.com/{id}_{size}.jpg"},"isOrgWhiteList":false,"badge":{"identity":null,"best_answerer":{"topics":[],"description":"优秀回答者"}}}
     * column : {"slug":"WebNotes","name":"小道消息"}
     * tipjarState : inactivated
     * content :
     *
     *那天一个读者在线找我，上来就跟我谈技术。
     *
     *她：老师，我们全站启用 HTTPS 了，你觉得怎么样？
     *
     *我：没什么啊，不过你们是做什么的？
     *
     *她：我们是做网络直播的。
     *
     *我：… 直播网站启用全站 HTTPS ？有必要吗？你们是哪一个网站？
     *
     *她：斗鱼。斗鱼是目前国内首家，也是目前唯一全站升级到 HTTPS 的网络直播平台。<br></br>
     *
     *我不是网络直播的目标用户，不过，一个视频网站启用了全站 HTTPS ，说明还是有技术追求的，这个事情也是有技术含量的，可以聊一下。
     *
     *作为一个不懂技术，尤其是「不写代码」的离任 CTO，跟人家讨论技术是挺难为情的事儿。万一露馅了怎么办？所以，有不对的地方，欢迎指出。
     *
     *HTTPS 这事儿，国内不少网站也就是用来保护一下用户数据，用户登录的时候启用而已。更多时候，出于性能开销上的折衷考虑，基本上都是非加密传输数据。多媒体文件，尤其是流媒体，明文传输似乎不会出现什么安全问题，然而，实际上也会产生非常严重的后果，比如被恶意劫持流量，页面上动不动插入一个小广告。而访问者还以为这是受访站提供的广告。如果是正常的广告还可以忍，然而色情赌博虚假医疗广告比比皆是。这当然不是好的用户体验。
     *
     *所以，在我们这个特定环境下，重视 HTTPS 是应该的，更不用说出于保护用户隐私等目的而去部署 HTTPS 了。
     *
     *其实，作为一个技术团队负责人(CTO)，今年至少有几个关于 HTTPS 的标志性事件应该引起你的关注（要不可真不太合格）： 1. YouTube 启用 HTTPS 加密，全站 97% 的流量均通过 HTTPS 传输(还有 3% 是因为要兼容旧设备)。 1. Netflix 宣布将使用 HTTPS 加密流视频。流视频的加密传输是个技术难题，但 Netflix 无疑找到了一个平衡。 1. 苹果公司在 WWDC 2016 宣布，到 2017 年 1 月 1 日 App Store 中的所有应用都必须启用 App Transport Security 安全功能。App Transport Security（ATS）是苹果在 iOS 9 中引入的一项隐私保护功能，屏蔽明文 HTTP 资源加载，连接必须经过 HTTPS。
     *
     *从全球范围看，推动 HTTPS 技术的公司里，Google 最为积极，专门做了一个专题页面监控「各大热门网站的 HTTPS 实施情况」，而 Google 全站到目前已经有超过 85% 的链接启用了加密。对比之下，中国的互联网公司对这方面的重视程度并不够。所以，这位斗鱼的朋友带着一点自豪感来跟我讲这个事儿是有一定缘由的。
     *
     ***重视 HTTPS 是一种义务和责任**。
     *
     *见微知著，一个公司如果能在技术层面比别人做得更进一步，愿意做投入，去做一些改变用户体验的事情，其他地方想必也会花心思，据斗鱼介绍，一个直播房间已经可以支撑百万人同时发弹幕。单从技术的角度讲，用户对弹幕系统的要求其实也挺高的，需要能支撑高并发、实时性的系统，做起来同样存在难度和挑战。<br></br>
     *
     *一个有追求的技术人在这样的团队里会遇到技术上的挑战，当然工作成果也会得到重视。
     *
     *这也是我写这篇文章的主要原因。毫无疑问，本文由斗鱼直播平台约稿。
     * state : published
     * sourceUrl :
     * pageCommentsCount : 2
     * canComment : false
     * snapshotUrl :
     * slug : 23480182
     * publishedTime : 2016-11-07T13:53:29+08:00
     * url : /p/23480182
     * title : HTTPS 全站加密可能是大势所趋
     * lastestLikers : [{"profileUrl":"https://www.zhihu.com/people/spencerfronzen","bio":"Operation","hash":"f5c36b3d17812fe5c89e4491c8d964c7","uid":28555849236480,"isOrg":false,"description":"","isOrgWhiteList":false,"slug":"spencerfronzen","avatar":{"id":"8f62800f7","template":"https://pic4.zhimg.com/{id}_{size}.jpg"},"name":"SpencerFronzen"},{"profileUrl":"https://www.zhihu.com/people/ichengzi","bio":"NULL","hash":"051ae92a6c46352e5008afb8ebc5a447","uid":28347119697920,"isOrg":false,"description":"","isOrgWhiteList":false,"slug":"ichengzi","avatar":{"id":"c8b51e0d3","template":"https://pic4.zhimg.com/{id}_{size}.jpg"},"name":"chengzi"},{"profileUrl":"https://www.zhihu.com/people/zhang-yang-55-52-89","bio":"来都来了","hash":"187ce036f3efc088c6b192755dbf7eb3","uid":61051995422720,"isOrg":false,"description":"","isOrgWhiteList":false,"slug":"zhang-yang-55-52-89","avatar":{"id":"c72ee396a2dc0dbfa0dbfc9e98d35dc4","template":"https://pic1.zhimg.com/{id}_{size}.jpg"},"name":"张扬"},{"profileUrl":"https://www.zhihu.com/people/luke","bio":"产品经理","hash":"72c1e2b58deab0be4ee0c54ade00845a","uid":26710074458112,"isOrg":false,"description":"","isOrgWhiteList":false,"slug":"luke","avatar":{"id":"6a04e6646","template":"https://pic3.zhimg.com/{id}_{size}.jpg"},"name":"游焰刚"},{"profileUrl":"https://www.zhihu.com/people/luocanfeng","bio":"","hash":"8277bd4228165fa10eb263f9a9dcd493","uid":26858431184896,"isOrg":false,"description":"","isOrgWhiteList":false,"slug":"luocanfeng","avatar":{"id":"d82868371","template":"https://pic2.zhimg.com/{id}_{size}.jpg"},"name":"罗灿锋"}]
     * summary : 那天一个读者在线找我，上来就跟我谈技术。她：老师，我们全站启用 HTTPS 了，你觉得怎么样？我：没什么啊，不过你们是做什么的？她：我们是做网络直播的。我：… 直播网站启用全站 HTTPS ？有必要吗？你们是哪一个网站？她：斗鱼。斗鱼是目前国内首家，也…
     * reviewingCommentsCount : 0
     * meta : {"previous":{"isTitleImageFullScreen":false,"rating":"none","titleImage":"https://pic1.zhimg.com/v2-ab3181c294cd0558a729d6a7d3b53940_r.jpg","links":{"comments":"/api/posts/23342653/comments"},"topics":[{"url":"https://www.zhihu.com/topic/19569604","id":"19569604","name":"个人所得税"}],"href":"/api/posts/23342653","excerptTitle":"","author":{"profileUrl":"https://www.zhihu.com/people/fenng","bio":"「小道消息」，微信号：WebNotes","hash":"99953853cc4219fabe8327301058357c","uid":26676083818496,"isOrg":false,"description":"手艺人.\n知乎74号用户.\n微信公众帐号「小道消息」, 微信号: 「WebNotes」\n\n黑我的人最后都变成了我的粉，需要新的黑子。","isOrgWhiteList":false,"slug":"fenng","avatar":{"id":"9ee82f3f07623303aa42164b523ac267","template":"https://pic4.zhimg.com/{id}_{size}.jpg"},"name":"Fenng"},"column":{"slug":"WebNotes","name":"小道消息"},"content":"
     *
     *前几天热议「年收入 12 万可能要加税」的话题，看了不少人转发相关文章，朋友圈都被刷屏了，很容易产生一种遍地「高收入」群体的错觉。不过，怕是很少有人思考这个基本问题：全国年收入超过 12 万的纳税人到底有多少？<\/p>
     *
     *在朋友圈做了一个小调查，绝大多数人的反馈挺让我惊讶，大家都太乐观了。<br></br><\/p>
     *
     *全国年收入超过 12 万的纳税人没有你想像的那么多。比大多数人直觉上预估的人数要少得多。<br></br><\/p>
     *
     *先摘录一组公开数据：<\/p> 1. 厦门，2015 年 12 万元以上个人所得税自行纳税申报的有 8.6 万人（包括 4614 名外国人）。厦门 2015 年申报个人所得税有 412.4 万人，其中，年收入超过 12 万元的有 8.6 万人，占申报个税总人数的 2.1%。数据来源：海西晨报。<\/li> 1. 苏州，2015 年度年所得 12 万元以上个人所得税自行纳税申报人数达 217837 人。数据来源：新华日报。另据苏州日报的数据是 16 万人，这里取最高值。<\/li> 1. 天津，2014 年度年所得 12 万元以上自行纳税申报人数超 16 万。数据来源：每日新报。<\/li> 1. 武汉，2014 年度年入 12 万以上者超 8.6 万人。数据来源：湖北日报。<br></br><\/li> 1. 安徽，2014 年度 「年收入超过 12 万元」自行申报个税人数达 77256 人。数据来源：新安晚报。<\/li> 1. 辽宁省 2015 年度年收入 12 万元以上个人申报的总人数为 106884 人(不包括大连)。数据来源：辽宁日报。<\/li> 1. 河南省 2015 年共受理年所得 12 万元以上纳税人自行纳税申报 141748 人。数据来源：河南日报。<\/li> 1. 江西，2014 年度年所得 12 万元以上个税自行纳税申报全省共 65576 人。数据来源：中国江西网-信息日报。<\/li> 1. 西藏，2014 年度年所得 12 万元个税自行申报首次突破万人。数据来源：中国西藏新闻网。<\/li><\/ol>
     *
     *根据这些数据，我们可以得到一个大致推断，年所得 12 万以上的纳税人没那么多，而一些人口大省，年所得 12 万以上的纳税人还不如一个经济发达的地级城市多。<\/p>
     *
     *我们再继续看公开数据，2010 年我国个人所得税约 4837 亿元，2011 年我国个人所得税收入 6054 亿元，2012 年我国个人所得税实现收入 5820.24 亿元(略有下降的原因是从 2011 年 9 月 1 日起，个人所得税的起征点调整为 3500 元)，2014 年个人所得税税收总共为 7,376.57 亿元，2015 年的个人所得税为 8618 亿元。<\/p>
     *
     *财新网报道，2010 年中国所得 12 万元纳税申报人数为 315 万，比 2009 年增加 46 万多人。2006 年，申报人数不到 163 万，该年度这个群体人均缴税额 49733 元。<br></br><\/p>
     *
     *在个税起征点调整之前，「2008 年年所得 12 万元以上纳税人自行申报的人数为 240 万人，占全国个人所得税纳税人数的约 3％，而缴纳的税额为 1294 亿元，占全国个人所得税总收入的 35%。」数据来源：中国政府网。从这个数据推算，** 2008 年，缴纳个税总人数约为 8000 万人<\/b>。<br></br><\/p>**
     *
     *个税起征点调高之后呢？全国政协委员、财政部财政科学研究所原所长贾康在 2015 年接受京华时报的采访中说：「现在交个税的人很少，只有 2800 万人，占整个人口总数的不到 2%」。<br></br><\/p>
     *
     *另据中国经营报的一篇文章称：「2011 年 9 月 1 日之后，工薪所得税费用减除标准提高到 3500 元，而个税法修改后，纳税人数由约 8400 万人减至约 2400 万人。」<br></br><\/p>
     *
     *注意，这两个数据并不矛盾。因为年度不同，随着经济增长，个税法修改，缴纳个税人数从 2400 万增加到 2800 万人是正常的。<br></br><\/p>
     *
     *从以上这些数据大致估算一下，2015 年**年收入超过 12 万的纳税申报人总数 550 万左右，误差正负 50 万<\/b>，因为最近两年经济波动实在是大。最低值 500 万，人均 5 万年缴税额(参考 2006 年该群体人均缴税额 49733 元)，缴税 2500 亿；最高值 600 万，人均 5 万年缴税额，缴税 3000 亿。3000 亿的话，对比去年总税额 8618 亿，差不多是 35%，份额跟 2008 年的 35% 大约持平。当然，如果你是个认真的人，可以根据上面的数据做个模型，再算一下，我只是估算，毕竟缺乏最新的有效数据支撑。<br></br><\/p>**
     *
     *必须说明的是，以上结论并非十分严谨，误差不小。而且，至少应该考虑可能存在的问题是，人均 5 万的纳税额现在是否已经有变化？<br></br><\/p>
     *
     *可以得到的结论是，「年收入 12 万可能要加税」应该是误解(随后也有官方辟谣)，对这几百万人加税，意义不大。因为全国年所得超过 12 万的肯定不只是申报的这几百万人，这也是我们必须要强调的一点。<br></br><\/p>
     *
     *一个业余选手研究一下数据，希望能给你一点小小的启发。<\/p>
     *
     *题图：Self-portrait with Bandaged Ear by Vincent van Gogh<br></br><\/p><br></br>
     *
     *讨论点击[](\"https://link.zhihu.com/?target=https%3A//wx.xiaomiquan.com/mweb/views/joingroup/join_group.html%3Fgroup_id%3D728151117%26secret%3D56ipgdwr39q94572gxaf3khlcc208rdc%26extra%3D488fc61f3913e4530a252c9bb11c09b70ee93f176ce80fd8aa1ef45753cb71d4\")" target=\"_blank\" rel=\"nofollow noreferrer\">这里*<\/i><\/a><\/p>","state":"published","sourceUrl":"","pageCommentsCount":0,"canComment":false,"snapshotUrl":"","slug":23342653,"publishedTime":"2016-11-01T11:31:52+08:00","url":"/p/23342653","title":"全国年收入超过 12 万的纳税人有多少？","summary":"前几天热议「年收入 12 万可能要加税」的话题，看了不少人转发相关文章，朋友圈都被刷屏了，很容易产生一种遍地「高收入」群体的错觉。不过，怕是很少有人思考这个基本问题：全国年收入超过 12 万的纳税人到底有多少？在朋友圈做了一个小调查，绝大多数人的\u2026","reviewingCommentsCount":0,"meta":{"previous":null,"next":null},"commentPermission":"friends","commentsCount":0,"likesCount":0},"next":null}
     * commentPermission : friends
     * commentsCount : 2
     * likesCount : 84
     * */

    var isIsTitleImageFullScreen: Boolean = false
    var rating: String? = null
    var titleImage: String? = null
    var links: LinksBean? = null
    var titleImageSize: TitleImageSizeBean? = null
    var href: String? = null
    var excerptTitle: String? = null
    var author: AuthorBean? = null
    var column: ColumnBean? = null
    var tipjarState: String? = null
    var content: String? = null
    var state: String? = null
    var sourceUrl: String? = null
    var pageCommentsCount: Int = 0
    var isCanComment: Boolean = false
    var snapshotUrl: String? = null
    var slug: Int = 0
    var publishedTime: String? = null
    var url: String? = null
    var title: String? = null
    var summary: String? = null
    var reviewingCommentsCount: Int = 0
    var meta: MetaBeanX? = null
    var commentPermission: String? = null
    var commentsCount: Int = 0
    var likesCount: Int = 0
    var reviewers: List<*>? = null
    var topics: List<TopicsBeanX>? = null
    var lastestLikers: List<LastestLikersBean>? = null

    class LinksBean {
        /**
         * comments : /api/posts/23480182/comments
         */

        var comments: String? = null
    }

    class TitleImageSizeBean {
        /**
         * width : 0
         * height : 0
         */

        var width: Int = 0
        var height: Int = 0
    }

    class AuthorBean {
        /**
         * bio : 「小道消息」，微信号：WebNotes
         * hash : 99953853cc4219fabe8327301058357c
         * uid : 26676083818496
         * isOrg : false
         * slug : fenng
         * description : 手艺人.
         * 知乎74号用户.
         * 微信公众帐号「小道消息」, 微信号: 「WebNotes」
         *
         *
         * 黑我的人最后都变成了我的粉，需要新的黑子。
         * name : Fenng
         * profileUrl : https://www.zhihu.com/people/fenng
         * avatar : {"id":"9ee82f3f07623303aa42164b523ac267","template":"https://pic4.zhimg.com/{id}_{size}.jpg"}
         * isOrgWhiteList : false
         * badge : {"identity":null,"best_answerer":{"topics":[],"description":"优秀回答者"}}
         */

        var bio: String? = null
        var hash: String? = null
        var uid: Long = 0
        var isIsOrg: Boolean = false
        var slug: String? = null
        var description: String? = null
        var name: String? = null
        var profileUrl: String? = null
        var avatar: AvatarBean? = null
        var isIsOrgWhiteList: Boolean = false
        var badge: BadgeBean? = null

        class AvatarBean {
            /**
             * id : 9ee82f3f07623303aa42164b523ac267
             * template : https://pic4.zhimg.com/{id}_{size}.jpg
             */

            var id: String? = null
            var template: String? = null
        }

        class BadgeBean {
            /**
             * identity : null
             * best_answerer : {"topics":[],"description":"优秀回答者"}
             */

            var identity: Any? = null
            var best_answerer: BestAnswererBean? = null

            class BestAnswererBean {
                /**
                 * topics : []
                 * description : 优秀回答者
                 */

                var description: String? = null
                var topics: List<*>? = null
            }
        }
    }

    class ColumnBean {
        /**
         * slug : WebNotes
         * name : 小道消息
         */

        var slug: String? = null
        var name: String? = null
    }

    class MetaBeanX {
        /**
         * previous : {"isTitleImageFullScreen":false,"rating":"none","titleImage":"https://pic1.zhimg.com/v2-ab3181c294cd0558a729d6a7d3b53940_r.jpg","links":{"comments":"/api/posts/23342653/comments"},"topics":[{"url":"https://www.zhihu.com/topic/19569604","id":"19569604","name":"个人所得税"}],"href":"/api/posts/23342653","excerptTitle":"","author":{"profileUrl":"https://www.zhihu.com/people/fenng","bio":"「小道消息」，微信号：WebNotes","hash":"99953853cc4219fabe8327301058357c","uid":26676083818496,"isOrg":false,"description":"手艺人.\n知乎74号用户.\n微信公众帐号「小道消息」, 微信号: 「WebNotes」\n\n黑我的人最后都变成了我的粉，需要新的黑子。","isOrgWhiteList":false,"slug":"fenng","avatar":{"id":"9ee82f3f07623303aa42164b523ac267","template":"https://pic4.zhimg.com/{id}_{size}.jpg"},"name":"Fenng"},"column":{"slug":"WebNotes","name":"小道消息"},"content":"
         *
         *前几天热议「年收入 12 万可能要加税」的话题，看了不少人转发相关文章，朋友圈都被刷屏了，很容易产生一种遍地「高收入」群体的错觉。不过，怕是很少有人思考这个基本问题：全国年收入超过 12 万的纳税人到底有多少？<\/p>
         *
         *在朋友圈做了一个小调查，绝大多数人的反馈挺让我惊讶，大家都太乐观了。<br></br><\/p>
         *
         *全国年收入超过 12 万的纳税人没有你想像的那么多。比大多数人直觉上预估的人数要少得多。<br></br><\/p>
         *
         *先摘录一组公开数据：<\/p> 1. 厦门，2015 年 12 万元以上个人所得税自行纳税申报的有 8.6 万人（包括 4614 名外国人）。厦门 2015 年申报个人所得税有 412.4 万人，其中，年收入超过 12 万元的有 8.6 万人，占申报个税总人数的 2.1%。数据来源：海西晨报。<\/li> 1. 苏州，2015 年度年所得 12 万元以上个人所得税自行纳税申报人数达 217837 人。数据来源：新华日报。另据苏州日报的数据是 16 万人，这里取最高值。<\/li> 1. 天津，2014 年度年所得 12 万元以上自行纳税申报人数超 16 万。数据来源：每日新报。<\/li> 1. 武汉，2014 年度年入 12 万以上者超 8.6 万人。数据来源：湖北日报。<br></br><\/li> 1. 安徽，2014 年度 「年收入超过 12 万元」自行申报个税人数达 77256 人。数据来源：新安晚报。<\/li> 1. 辽宁省 2015 年度年收入 12 万元以上个人申报的总人数为 106884 人(不包括大连)。数据来源：辽宁日报。<\/li> 1. 河南省 2015 年共受理年所得 12 万元以上纳税人自行纳税申报 141748 人。数据来源：河南日报。<\/li> 1. 江西，2014 年度年所得 12 万元以上个税自行纳税申报全省共 65576 人。数据来源：中国江西网-信息日报。<\/li> 1. 西藏，2014 年度年所得 12 万元个税自行申报首次突破万人。数据来源：中国西藏新闻网。<\/li><\/ol>
         *
         *根据这些数据，我们可以得到一个大致推断，年所得 12 万以上的纳税人没那么多，而一些人口大省，年所得 12 万以上的纳税人还不如一个经济发达的地级城市多。<\/p>
         *
         *我们再继续看公开数据，2010 年我国个人所得税约 4837 亿元，2011 年我国个人所得税收入 6054 亿元，2012 年我国个人所得税实现收入 5820.24 亿元(略有下降的原因是从 2011 年 9 月 1 日起，个人所得税的起征点调整为 3500 元)，2014 年个人所得税税收总共为 7,376.57 亿元，2015 年的个人所得税为 8618 亿元。<\/p>
         *
         *财新网报道，2010 年中国所得 12 万元纳税申报人数为 315 万，比 2009 年增加 46 万多人。2006 年，申报人数不到 163 万，该年度这个群体人均缴税额 49733 元。<br></br><\/p>
         *
         *在个税起征点调整之前，「2008 年年所得 12 万元以上纳税人自行申报的人数为 240 万人，占全国个人所得税纳税人数的约 3％，而缴纳的税额为 1294 亿元，占全国个人所得税总收入的 35%。」数据来源：中国政府网。从这个数据推算，** 2008 年，缴纳个税总人数约为 8000 万人<\/b>。<br></br><\/p>**
         *
         *个税起征点调高之后呢？全国政协委员、财政部财政科学研究所原所长贾康在 2015 年接受京华时报的采访中说：「现在交个税的人很少，只有 2800 万人，占整个人口总数的不到 2%」。<br></br><\/p>
         *
         *另据中国经营报的一篇文章称：「2011 年 9 月 1 日之后，工薪所得税费用减除标准提高到 3500 元，而个税法修改后，纳税人数由约 8400 万人减至约 2400 万人。」<br></br><\/p>
         *
         *注意，这两个数据并不矛盾。因为年度不同，随着经济增长，个税法修改，缴纳个税人数从 2400 万增加到 2800 万人是正常的。<br></br><\/p>
         *
         *从以上这些数据大致估算一下，2015 年**年收入超过 12 万的纳税申报人总数 550 万左右，误差正负 50 万<\/b>，因为最近两年经济波动实在是大。最低值 500 万，人均 5 万年缴税额(参考 2006 年该群体人均缴税额 49733 元)，缴税 2500 亿；最高值 600 万，人均 5 万年缴税额，缴税 3000 亿。3000 亿的话，对比去年总税额 8618 亿，差不多是 35%，份额跟 2008 年的 35% 大约持平。当然，如果你是个认真的人，可以根据上面的数据做个模型，再算一下，我只是估算，毕竟缺乏最新的有效数据支撑。<br></br><\/p>**
         *
         *必须说明的是，以上结论并非十分严谨，误差不小。而且，至少应该考虑可能存在的问题是，人均 5 万的纳税额现在是否已经有变化？<br></br><\/p>
         *
         *可以得到的结论是，「年收入 12 万可能要加税」应该是误解(随后也有官方辟谣)，对这几百万人加税，意义不大。因为全国年所得超过 12 万的肯定不只是申报的这几百万人，这也是我们必须要强调的一点。<br></br><\/p>
         *
         *一个业余选手研究一下数据，希望能给你一点小小的启发。<\/p>
         *
         *题图：Self-portrait with Bandaged Ear by Vincent van Gogh<br></br><\/p><br></br>
         *
         *讨论点击[](\"https://link.zhihu.com/?target=https%3A//wx.xiaomiquan.com/mweb/views/joingroup/join_group.html%3Fgroup_id%3D728151117%26secret%3D56ipgdwr39q94572gxaf3khlcc208rdc%26extra%3D488fc61f3913e4530a252c9bb11c09b70ee93f176ce80fd8aa1ef45753cb71d4\")" target=\"_blank\" rel=\"nofollow noreferrer\">这里*<\/i><\/a><\/p>","state":"published","sourceUrl":"","pageCommentsCount":0,"canComment":false,"snapshotUrl":"","slug":23342653,"publishedTime":"2016-11-01T11:31:52+08:00","url":"/p/23342653","title":"全国年收入超过 12 万的纳税人有多少？","summary":"前几天热议「年收入 12 万可能要加税」的话题，看了不少人转发相关文章，朋友圈都被刷屏了，很容易产生一种遍地「高收入」群体的错觉。不过，怕是很少有人思考这个基本问题：全国年收入超过 12 万的纳税人到底有多少？在朋友圈做了一个小调查，绝大多数人的\u2026","reviewingCommentsCount":0,"meta":{"previous":null,"next":null},"commentPermission":"friends","commentsCount":0,"likesCount":0}
         * next : null
         * */

        var previous: PreviousBean? = null
        var next: Any? = null

        class PreviousBean {
            /**
             * isTitleImageFullScreen : false
             * rating : none
             * titleImage : https://pic1.zhimg.com/v2-ab3181c294cd0558a729d6a7d3b53940_r.jpg
             * links : {"comments":"/api/posts/23342653/comments"}
             * topics : [{"url":"https://www.zhihu.com/topic/19569604","id":"19569604","name":"个人所得税"}]
             * href : /api/posts/23342653
             * excerptTitle :
             * author : {"profileUrl":"https://www.zhihu.com/people/fenng","bio":"「小道消息」，微信号：WebNotes","hash":"99953853cc4219fabe8327301058357c","uid":26676083818496,"isOrg":false,"description":"手艺人.\n知乎74号用户.\n微信公众帐号「小道消息」, 微信号: 「WebNotes」\n\n黑我的人最后都变成了我的粉，需要新的黑子。","isOrgWhiteList":false,"slug":"fenng","avatar":{"id":"9ee82f3f07623303aa42164b523ac267","template":"https://pic4.zhimg.com/{id}_{size}.jpg"},"name":"Fenng"}
             * column : {"slug":"WebNotes","name":"小道消息"}
             * content :
             *
             *前几天热议「年收入 12 万可能要加税」的话题，看了不少人转发相关文章，朋友圈都被刷屏了，很容易产生一种遍地「高收入」群体的错觉。不过，怕是很少有人思考这个基本问题：全国年收入超过 12 万的纳税人到底有多少？
             *
             *在朋友圈做了一个小调查，绝大多数人的反馈挺让我惊讶，大家都太乐观了。<br></br>
             *
             *全国年收入超过 12 万的纳税人没有你想像的那么多。比大多数人直觉上预估的人数要少得多。<br></br>
             *
             *先摘录一组公开数据： 1. 厦门，2015 年 12 万元以上个人所得税自行纳税申报的有 8.6 万人（包括 4614 名外国人）。厦门 2015 年申报个人所得税有 412.4 万人，其中，年收入超过 12 万元的有 8.6 万人，占申报个税总人数的 2.1%。数据来源：海西晨报。 1. 苏州，2015 年度年所得 12 万元以上个人所得税自行纳税申报人数达 217837 人。数据来源：新华日报。另据苏州日报的数据是 16 万人，这里取最高值。 1. 天津，2014 年度年所得 12 万元以上自行纳税申报人数超 16 万。数据来源：每日新报。 1. 武汉，2014 年度年入 12 万以上者超 8.6 万人。数据来源：湖北日报。<br></br> 1. 安徽，2014 年度 「年收入超过 12 万元」自行申报个税人数达 77256 人。数据来源：新安晚报。 1. 辽宁省 2015 年度年收入 12 万元以上个人申报的总人数为 106884 人(不包括大连)。数据来源：辽宁日报。 1. 河南省 2015 年共受理年所得 12 万元以上纳税人自行纳税申报 141748 人。数据来源：河南日报。 1. 江西，2014 年度年所得 12 万元以上个税自行纳税申报全省共 65576 人。数据来源：中国江西网-信息日报。 1. 西藏，2014 年度年所得 12 万元个税自行申报首次突破万人。数据来源：中国西藏新闻网。
             *
             *根据这些数据，我们可以得到一个大致推断，年所得 12 万以上的纳税人没那么多，而一些人口大省，年所得 12 万以上的纳税人还不如一个经济发达的地级城市多。
             *
             *我们再继续看公开数据，2010 年我国个人所得税约 4837 亿元，2011 年我国个人所得税收入 6054 亿元，2012 年我国个人所得税实现收入 5820.24 亿元(略有下降的原因是从 2011 年 9 月 1 日起，个人所得税的起征点调整为 3500 元)，2014 年个人所得税税收总共为 7,376.57 亿元，2015 年的个人所得税为 8618 亿元。
             *
             *财新网报道，2010 年中国所得 12 万元纳税申报人数为 315 万，比 2009 年增加 46 万多人。2006 年，申报人数不到 163 万，该年度这个群体人均缴税额 49733 元。<br></br>
             *
             *在个税起征点调整之前，「2008 年年所得 12 万元以上纳税人自行申报的人数为 240 万人，占全国个人所得税纳税人数的约 3％，而缴纳的税额为 1294 亿元，占全国个人所得税总收入的 35%。」数据来源：中国政府网。从这个数据推算，** 2008 年，缴纳个税总人数约为 8000 万人**。<br></br>
             *
             *个税起征点调高之后呢？全国政协委员、财政部财政科学研究所原所长贾康在 2015 年接受京华时报的采访中说：「现在交个税的人很少，只有 2800 万人，占整个人口总数的不到 2%」。<br></br>
             *
             *另据中国经营报的一篇文章称：「2011 年 9 月 1 日之后，工薪所得税费用减除标准提高到 3500 元，而个税法修改后，纳税人数由约 8400 万人减至约 2400 万人。」<br></br>
             *
             *注意，这两个数据并不矛盾。因为年度不同，随着经济增长，个税法修改，缴纳个税人数从 2400 万增加到 2800 万人是正常的。<br></br>
             *
             *从以上这些数据大致估算一下，2015 年**年收入超过 12 万的纳税申报人总数 550 万左右，误差正负 50 万**，因为最近两年经济波动实在是大。最低值 500 万，人均 5 万年缴税额(参考 2006 年该群体人均缴税额 49733 元)，缴税 2500 亿；最高值 600 万，人均 5 万年缴税额，缴税 3000 亿。3000 亿的话，对比去年总税额 8618 亿，差不多是 35%，份额跟 2008 年的 35% 大约持平。当然，如果你是个认真的人，可以根据上面的数据做个模型，再算一下，我只是估算，毕竟缺乏最新的有效数据支撑。<br></br>
             *
             *必须说明的是，以上结论并非十分严谨，误差不小。而且，至少应该考虑可能存在的问题是，人均 5 万的纳税额现在是否已经有变化？<br></br>
             *
             *可以得到的结论是，「年收入 12 万可能要加税」应该是误解(随后也有官方辟谣)，对这几百万人加税，意义不大。因为全国年所得超过 12 万的肯定不只是申报的这几百万人，这也是我们必须要强调的一点。<br></br>
             *
             *一个业余选手研究一下数据，希望能给你一点小小的启发。
             *
             *题图：Self-portrait with Bandaged Ear by Vincent van Gogh<br></br><br></br>
             *
             *讨论点击[这里**](https://link.zhihu.com/?target=https%3A//wx.xiaomiquan.com/mweb/views/joingroup/join_group.html%3Fgroup_id%3D728151117%26secret%3D56ipgdwr39q94572gxaf3khlcc208rdc%26extra%3D488fc61f3913e4530a252c9bb11c09b70ee93f176ce80fd8aa1ef45753cb71d4)
             * state : published
             * sourceUrl :
             * pageCommentsCount : 0
             * canComment : false
             * snapshotUrl :
             * slug : 23342653
             * publishedTime : 2016-11-01T11:31:52+08:00
             * url : /p/23342653
             * title : 全国年收入超过 12 万的纳税人有多少？
             * summary : 前几天热议「年收入 12 万可能要加税」的话题，看了不少人转发相关文章，朋友圈都被刷屏了，很容易产生一种遍地「高收入」群体的错觉。不过，怕是很少有人思考这个基本问题：全国年收入超过 12 万的纳税人到底有多少？在朋友圈做了一个小调查，绝大多数人的…
             * reviewingCommentsCount : 0
             * meta : {"previous":null,"next":null}
             * commentPermission : friends
             * commentsCount : 0
             * likesCount : 0
             */

            var isIsTitleImageFullScreen: Boolean = false
            var rating: String? = null
            var titleImage: String? = null
            var links: LinksBeanX? = null
            var href: String? = null
            var excerptTitle: String? = null
            var author: AuthorBeanX? = null
            var column: ColumnBeanX? = null
            var content: String? = null
            var state: String? = null
            var sourceUrl: String? = null
            var pageCommentsCount: Int = 0
            var isCanComment: Boolean = false
            var snapshotUrl: String? = null
            var slug: Int = 0
            var publishedTime: String? = null
            var url: String? = null
            var title: String? = null
            var summary: String? = null
            var reviewingCommentsCount: Int = 0
            var meta: MetaBean? = null
            var commentPermission: String? = null
            var commentsCount: Int = 0
            var likesCount: Int = 0
            var topics: List<TopicsBean>? = null

            class LinksBeanX {
                /**
                 * comments : /api/posts/23342653/comments
                 */

                var comments: String? = null
            }

            class AuthorBeanX {
                /**
                 * profileUrl : https://www.zhihu.com/people/fenng
                 * bio : 「小道消息」，微信号：WebNotes
                 * hash : 99953853cc4219fabe8327301058357c
                 * uid : 26676083818496
                 * isOrg : false
                 * description : 手艺人.
                 * 知乎74号用户.
                 * 微信公众帐号「小道消息」, 微信号: 「WebNotes」
                 *
                 *
                 * 黑我的人最后都变成了我的粉，需要新的黑子。
                 * isOrgWhiteList : false
                 * slug : fenng
                 * avatar : {"id":"9ee82f3f07623303aa42164b523ac267","template":"https://pic4.zhimg.com/{id}_{size}.jpg"}
                 * name : Fenng
                 */

                var profileUrl: String? = null
                var bio: String? = null
                var hash: String? = null
                var uid: Long = 0
                var isIsOrg: Boolean = false
                var description: String? = null
                var isIsOrgWhiteList: Boolean = false
                var slug: String? = null
                var avatar: AvatarBeanX? = null
                var name: String? = null

                class AvatarBeanX {
                    /**
                     * id : 9ee82f3f07623303aa42164b523ac267
                     * template : https://pic4.zhimg.com/{id}_{size}.jpg
                     */

                    var id: String? = null
                    var template: String? = null
                }
            }

            class ColumnBeanX {
                /**
                 * slug : WebNotes
                 * name : 小道消息
                 */

                var slug: String? = null
                var name: String? = null
            }

            class MetaBean {
                /**
                 * previous : null
                 * next : null
                 */

                var previous: Any? = null
                var next: Any? = null
            }

            class TopicsBean {
                /**
                 * url : https://www.zhihu.com/topic/19569604
                 * id : 19569604
                 * name : 个人所得税
                 */

                var url: String? = null
                var id: String? = null
                var name: String? = null
            }
        }
    }

    class TopicsBeanX {
        /**
         * url : https://www.zhihu.com/topic/19568367
         * id : 19568367
         * name : HTTPS
         */

        var url: String? = null
        var id: String? = null
        var name: String? = null
    }

    class LastestLikersBean {
        /**
         * profileUrl : https://www.zhihu.com/people/spencerfronzen
         * bio : Operation
         * hash : f5c36b3d17812fe5c89e4491c8d964c7
         * uid : 28555849236480
         * isOrg : false
         * description :
         * isOrgWhiteList : false
         * slug : spencerfronzen
         * avatar : {"id":"8f62800f7","template":"https://pic4.zhimg.com/{id}_{size}.jpg"}
         * name : SpencerFronzen
         */

        var profileUrl: String? = null
        var bio: String? = null
        var hash: String? = null
        var uid: Long = 0
        var isIsOrg: Boolean = false
        var description: String? = null
        var isIsOrgWhiteList: Boolean = false
        var slug: String? = null
        var avatar: AvatarBeanXX? = null
        var name: String? = null

        class AvatarBeanXX {
            /**
             * id : 8f62800f7
             * template : https://pic4.zhimg.com/{id}_{size}.jpg
             */

            var id: String? = null
            var template: String? = null
        }
    }
}
