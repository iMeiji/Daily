package com.meiji.daily.bean

/**
 * Created by Meiji on 2018/2/9.
 */

data class PostsListBean(
		val isTitleImageFullScreen: Boolean, //false
		val rating: String, //none
		val sourceUrl: String,
		val publishedTime: String, //2018-01-29T15:45:38+08:00
		val links: Links,
		val author: Author,
		val url: String, ///p/33398689
		val title: String, //发布了一款叫做「抽奖助手」的小程序
		val titleImage: String, //https://pic4.zhimg.com/v2-de4b8114e8408d5265503c8b41f59f85_r.jpg
		val summary: String,
		val content: String, //<p>无码科技团队前不久发布了一款叫做「抽奖助手」的小程序。目前在用户那边反馈还不错。</p><p>这个小程序主要解决如下三个场景的需求：</p><ul><li>公众号作者抽奖活动</li><li>微信群里抽奖</li><li>年会抽奖</li></ul><p>先说第一个需求，也是我自己的需求。有的时候想做个活动，回馈一下公众号读者，怎么做，又公平又简单呢？现在有些公众号做活动，都是写评论，点赞最多的算中奖。可是呢，你还要提示参与者写快递信息什么的。不优雅，很麻烦。</p><p>微信群里，如果抽奖，该怎么弄呢？现在也没看到很好的方式。当然，你可以用发红包，然后选最大红包或是最小红包的方式抽奖，不过，红包小了金额会重复，也不好，不好玩。</p><p>公司年会抽奖，怎么搞？不但需要考虑公平性的问题，还要考虑弄这个抽奖活动要花多少成本，我还真见过有的公司为了年会单独写程序抽奖，发短信之类的，无形中多了很多成本，弄不好还当众掉链子。</p><p>有没有一个小工具解决这些问题，并且可以用完即走，还灵活优雅呢？我们发布的这个「抽奖助手」小程序或许可以解决上面提到的需求场景。</p><p>当然，这个小程序产品还在改进优化中，还可以有很多玩法。懂得运营的用户会发现它的更多场景，比如线下活动，要搞个抽奖活动，只需要把小程序的活动页面打印出来贴那儿就行，就这么简单。</p><hr><p>无码科技团队为什么又做小程序了呢？</p><p>微信团队对小程序寄予厚望，作为创业团队，也得积极探索可能性。</p><p>无码科技团队现在做的几款小程序，除了想触达更多用户，也是看看我们整体的技术协作能力，展示一下产品创新方面的能力。</p><p>更重要的是，这个事情很有趣啊。</p><p>咋使用？微信里搜索小程序「抽奖助手」即可。</p><img src="v2-de4b8114e8408d5265503c8b41f59f85.jpg" data-caption="" data-size="normal" data-rawwidth="430" data-rawheight="430"><p></p>
		val state: String, //published
		val href: String, ///api/posts/33398689
		val meta: Meta,
		val commentPermission: String, //review
		val snapshotUrl: String,
		val canComment: Boolean, //false
		val slug: Int, //33398689
		val commentsCount: Int, //23
		val likesCount: Int //27
) {
	data class Links(
			val comments: String ///api/posts/33398689/comments
	)

	data class Author(
			val bio: String, //「小道消息」，微信号：WebNotes
			val isFollowing: Boolean, //false
			val hash: String, //99953853cc4219fabe8327301058357c
			val uid: Long, //26676083818496
			val isOrg: Boolean, //false
			val slug: String, //fenng
			val isFollowed: Boolean, //false
			val description: String, //手艺人.知乎74号用户.微信公众帐号「小道消息」, 微信号: 「WebNotes」https://readhub.me
			val name: String, //Fenng
			val profileUrl: String, //https://www.zhihu.com/people/fenng
			val avatar: Avatar,
			val isOrgWhiteList: Boolean, //false
			val isBanned: Boolean //false
	) {
		data class Avatar(
				val id: String, //9ee82f3f07623303aa42164b523ac267
				val template: String //https://pic3.zhimg.com/{id}_{size}.jpg
		)
	}

	data class Meta(
			val previous: Any, //null
			val next: Any //null
	)
}