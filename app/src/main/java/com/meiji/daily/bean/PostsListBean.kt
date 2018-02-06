package com.meiji.daily.bean

/**
 * Created by Meiji on 2016/11/18.
 */
class PostsListBean {

    /**
     * isTitleImageFullScreen : false
     * rating : none
     * sourceUrl :
     * publishedTime : 2016-11-07T13:53:29+08:00
     * links : {"comments":"/api/posts/23480182/comments"}
     * author : {"profileUrl":"https://www.zhihu.com/people/fenng","bio":"「小道消息」，微信号：WebNotes","hash":"99953853cc4219fabe8327301058357c","uid":26676083818496,"isOrg":false,"description":"手艺人.\n知乎74号用户.\n微信公众帐号「小道消息」, 微信号: 「WebNotes」\n\n黑我的人最后都变成了我的粉，需要新的黑子。","isOrgWhiteList":false,"slug":"fenng","avatar":{"id":"9ee82f3f07623303aa42164b523ac267","template":"https://pic4.zhimg.com/{id}_{size}.jpg"},"name":"Fenng"}
     * url : /p/23480182
     * title : HTTPS 全站加密可能是大势所趋
     * titleImage : https://pic3.zhimg.com/v2-66f227a03683cd9f37b35892ab2f34a2_r.png
     * summary :
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
     * href : /api/posts/23480182
     * meta : {"previous":null,"next":null}
     * commentPermission : friends
     * snapshotUrl :
     * canComment : false
     * slug : 23480182
     * commentsCount : 2
     * likesCount : 84
     */

    var isIsTitleImageFullScreen: Boolean = false
    var rating: String? = null
    var sourceUrl: String? = null
    var publishedTime: String? = null
    var links: LinksBean? = null
    var author: AuthorBean? = null
    var url: String? = null
    var title: String? = null
    var titleImage: String? = null
    var summary: String? = null
    var content: String? = null
    var state: String? = null
    var href: String? = null
    var meta: MetaBean? = null
    var commentPermission: String? = null
    var snapshotUrl: String? = null
    var isCanComment: Boolean = false
    var slug: Int = 0
    var commentsCount: Int = 0
    var likesCount: Int = 0

    override fun equals(o: Any?): Boolean {
        if (this === o)
            return true
        if (o == null || javaClass != o.javaClass)
            return false

        val that = o as PostsListBean?

        return slug == that!!.slug
    }

    override fun hashCode(): Int {
        return slug
    }

    class LinksBean {
        /**
         * comments : /api/posts/23480182/comments
         */

        var comments: String? = null
    }

    class AuthorBean {
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
        var avatar: AvatarBean? = null
        var name: String? = null

        class AvatarBean {
            /**
             * id : 9ee82f3f07623303aa42164b523ac267
             * template : https://pic4.zhimg.com/{id}_{size}.jpg
             */

            var id: String? = null
            var template: String? = null
        }
    }

    class MetaBean {
        /**
         * previous : null
         * next : null
         */

        var previous: Any? = null
        var next: Any? = null
    }
}
