package com.meiji.daily.bean

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey


/**
 * Created by Meiji on 2018/2/9.
 */
@Entity(tableName = "zhuanlans")
data class ZhuanlanBean(
        var followersCount: Int, //28569
        var creator: Creator,
        var topics: List<Topic>,
        var activateState: String, //activated
        var href: String, ///api/columns/design
        var acceptSubmission: Boolean, //true
        var firstTime: Boolean, //false
//        var postTopics: List<PostTopic>,
        var pendingName: String,
        var avatar: Avatar,
        var canManage: Boolean, //false
        var description: String, //关于用户体验、产品 、技术 和创业的干货集中地 | 最美应用：zuimeia.com
//        var pendingTopics: List<Any>,
        var nameCanEditUntil: Int, //0
        var reason: String,
        var banUntil: Int, //0
        @PrimaryKey var slug: String, //design
        var name: String, //可能性 | 产品与大设计
        var url: String, ///design
        var intro: String, //马力的互联网产品设计与用户体验专栏
        var topicsCanEditUntil: Int, //0
        var activateAuthorRequested: String, //none
        var commentPermission: String, //anyone
        var following: Boolean, //false
        var postsCount: Int, //200
        var canPost: Boolean, //false
        var type: Int = 0
) {
    data class Topic(
            var url: String, //https://www.zhihu.com/topic/19550517
            var id: String, //19550517
            var name: String //互联网
    )

    data class PostTopic(
            var postsCount: Int, //1
            var id: Int, //2
            var name: String //知乎
    )

    data class Creator(
            var bio: String, //马力在招聘：zhuanlan.zhihu.com/p/31904197
            var isFollowing: Boolean, //false
            var hash: String, //c6e85ba5d5999df4c5ce2f2903b1ce0e
            var uid: Long, //26680571723776
            var isOrg: Boolean, //false
            var slug: String, //mali
            var isFollowed: Boolean, //false
            var description: String, //最美应用创始人 产品经理 设计师，欢迎关注微博：@Ma_Li | 他在好奇的注视着这个饶有趣味的世界 ｜ 感谢在评选知乎年度荣誉会员时为我投票的各位，一起认真！| 马力的互联网学习圈：http://mali.brixd.com     |  文章索引： https://zhuanlan.zhihu.com/p/25493627
            var name: String, //马力
            var profileUrl: String, //https://www.zhihu.com/people/mali
            var avatar: Avatar,
            var isOrgWhiteList: Boolean, //false
            var isBanned: Boolean //false
    ) {
        data class Avatar(
                var id: String, //ba332a401
                var template: String //https://pic2.zhimg.com/{id}_{size}.jpg
        )
    }

    data class Avatar(
            var id: String, //v2-5410cdcdc7fb1556a27d0ddc4734e64b
            var template: String //https://pic2.zhimg.com/{id}_{size}.jpg
    )
}