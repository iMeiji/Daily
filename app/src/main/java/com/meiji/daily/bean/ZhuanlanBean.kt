package com.meiji.daily.bean

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey

/**
 * Created by Meiji on 2016/11/16.
 */
@Entity(tableName = "zhuanlans")
class ZhuanlanBean {

    /**
     * followersCount : 22775
     * creator : {"profileUrl":"https://www.zhihu.com/people/mali","bio":"试试「最美有物」App","hash":"c6e85ba5d5999df4c5ce2f2903b1ce0e","uid":26680571723776,"isOrg":false,"description":"最美应用创始人 产品经理 设计师 | 他在好奇的注视着这个饶有趣味的世界 \n|  http://hi.zuimeia.com \n| 本人已委托维权骑士（http://rightknights.com/）为我的文章进行维权。","slug":"mali","avatar":{"id":"ba332a401","template":"https://pic2.zhimg.com/{id}_{size}.jpg"},"name":"马力"}
     * topics : [{"url":"https://www.zhihu.com/topic/19550517","id":"19550517","name":"互联网"},{"url":"https://www.zhihu.com/topic/19557593","id":"19557593","name":"互联网创业"}]
     * activateState : activated
     * href : /api/columns/design
     * acceptSubmission : false
     * firstTime : false
     * postTopics : [{"postsCount":27,"id":153,"name":"用户体验"},{"postsCount":24,"id":368,"name":"产品经理"},{"postsCount":1,"id":445,"name":"设计"},{"postsCount":11,"id":1990,"name":"交互设计"},{"postsCount":4,"id":3005,"name":"用户界面设计"},{"postsCount":20,"id":3384,"name":"产品设计师"},{"postsCount":1,"id":3835,"name":"用户体验设计"},{"postsCount":1,"id":7527,"name":"交互设计师"},{"postsCount":1,"id":10626,"name":"建筑设计"},{"postsCount":1,"id":166373,"name":"天宫二号"},{"postsCount":1,"id":167374,"name":"值乎"},{"postsCount":1,"id":168696,"name":"知乎 Live"}]
     * pendingName :
     * avatar : {"id":"v2-5410cdcdc7fb1556a27d0ddc4734e64b","template":"https://pic4.zhimg.com/{id}_{size}.jpg"}
     * canManage : false
     * description : 关于用户体验、产品 、技术 和创业的干货集中地 | 最美应用：zuimeia.com
     * pendingTopics : []
     * nameCanEditUntil : 0
     * reason :
     * banUntil : 0
     * slug : design
     * name : 可能性 | 产品与大设计
     * url : /design
     * intro : 马力的互联网产品设计与用户体验专栏
     * topicsCanEditUntil : 0
     * activateAuthorRequested : none
     * commentPermission : anyone
     * following : false
     * postsCount : 56
     * canPost : false
     */

    var followersCount: Int = 0
    @Ignore
    var creator: CreatorBean? = null
    var activateState: String? = null
    var href: String? = null
    var isAcceptSubmission: Boolean = false
    var isFirstTime: Boolean = false
    var pendingName: String? = null
    var avatar: AvatarBeanX? = null
    var isCanManage: Boolean = false
    var description: String? = null
    var nameCanEditUntil: Int = 0
    var reason: String? = null
    var banUntil: Int = 0
    @PrimaryKey
    lateinit var slug: String
    var name: String? = null
    var url: String? = null
    var intro: String? = null
    var topicsCanEditUntil: Int = 0
    var activateAuthorRequested: String? = null
    var commentPermission: String? = null
    var isFollowing: Boolean = false
    var postsCount: Int = 0
    var isCanPost: Boolean = false
    @Ignore
    var topics: List<TopicsBean>? = null
    @Ignore
    var postTopics: List<PostTopicsBean>? = null
    @Ignore
    var pendingTopics: List<*>? = null
    var type: Int = 0

    override fun equals(o: Any?): Boolean {
        if (this === o)
            return true
        if (o == null || javaClass != o.javaClass)
            return false

        val that = o as ZhuanlanBean?

        return slug == that!!.slug
    }

    override fun hashCode(): Int {
        return slug.hashCode()
    }

    class CreatorBean {
        /**
         * profileUrl : https://www.zhihu.com/people/mali
         * bio : 试试「最美有物」App
         * hash : c6e85ba5d5999df4c5ce2f2903b1ce0e
         * uid : 26680571723776
         * isOrg : false
         * description : 最美应用创始人 产品经理 设计师 | 他在好奇的注视着这个饶有趣味的世界
         * |  http://hi.zuimeia.com
         * | 本人已委托维权骑士（http://rightknights.com/）为我的文章进行维权。
         * slug : mali
         * avatar : {"id":"ba332a401","template":"https://pic2.zhimg.com/{id}_{size}.jpg"}
         * name : 马力
         */

        var profileUrl: String? = null
        var bio: String? = null
        var hash: String? = null
        var uid: Long = 0
        var isIsOrg: Boolean = false
        var description: String? = null
        var slug: String? = null
        var avatar: AvatarBean? = null
        var name: String? = null

        class AvatarBean {
            /**
             * id : ba332a401
             * template : https://pic2.zhimg.com/{id}_{size}.jpg
             */

            var id: String? = null
            var template: String? = null
        }
    }

    class AvatarBeanX {
        /**
         * id : v2-5410cdcdc7fb1556a27d0ddc4734e64b
         * template : https://pic4.zhimg.com/{id}_{size}.jpg
         */

        var id: String? = null
        var template: String? = null
    }

    class TopicsBean {
        /**
         * url : https://www.zhihu.com/topic/19550517
         * id : 19550517
         * name : 互联网
         */

        var url: String? = null
        var id: String? = null
        var name: String? = null
    }

    class PostTopicsBean {
        /**
         * postsCount : 27
         * id : 153
         * name : 用户体验
         */

        var postsCount: Int = 0
        var id: Int = 0
        var name: String? = null
    }
}
