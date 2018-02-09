package com.meiji.daily.bean

/**
 * Created by Meiji on 2018/2/9.
 */

data class PostsContentBean(
		val isTitleImageFullScreen: Boolean, //true
		val rating: String, //none
		val titleImage: String, //https://pic2.zhimg.com/v2-aea21f51ef694429a9270a45b49c66d3_r.jpg
		val links: Links,
		val reviewers: List<Any>,
		val topics: List<Topic>,
		val admin_closed_comment: Boolean, //false
		val titleImageSize: TitleImageSize,
		val href: String, ///api/posts/33717055
		val collapsedCount: Int, //0
		val excerptTitle: String,
		val author: Author,
		val column: Column,
		val tipjarState: String, //inactivated
		val content: String, //<p>【知群】是做“连接”的知识社群产品，连接学习者和“分享/招聘者”。在过去的半年时间里，【知群】以学习社群的方式与几十万互联网从业人员建立了连接，这其中包括了近千名资深专家、总监、副总裁以及一线实战的创始人所在的互联网学习圈，以及以UI和UX为主的设计师群体等。</p><p>具体来说，我们在做的事情是用社群的方式帮助互联网人学习、成长、找工作……</p><p>1.梳理知识体系，研发系统的、高质量的课程，避免过于碎片化的知识，让大家只要愿意花时间，就能学到东西；</p><p>2.邀请互联网行业的资深专业人士，来帮助正在成长中的人们。这些行业里的专业人士、大牛大咖们，刚好都和我们很熟，也愿意来指导行业中的后来者；</p><p>3.注重圈子的力量，培养社群，提供招聘、内推等的信息和机会，不断消除资源合作、招聘内推之中的信息不对称。</p><p><br></p><p>年后的3月份，【知群】将会上线 2018 年第一场「限时闪聘」活动，这是我们在招聘服务上进行的一次探索，让企业方和求职者实现更高效的对接。招聘会的规则是 ——</p><p><br></p><p><b>候选人在规定的时间内投递简历</b></p><p><b>团队负责人会在3小时内回复你的求职申请</b></p><p><br></p><p>以下是第一场「限时闪聘」活动的部分职位信息，我们预先提供给大家。因为是第一场，有些互联网圈内的朋友还不知道我们在做这件事，所以我们选择提前曝光这些职位，这样对职位感兴趣的朋友就不会错过这些工作机会。</p><figure><noscript><img src="https://pic1.zhimg.com/v2-99c2f396618749e6cd268015a6a76566_b.jpg" data-caption="" data-size="normal" data-rawwidth="1074" data-rawheight="1497" class="origin_image zh-lightbox-thumb" width="1074" data-original="https://pic1.zhimg.com/v2-99c2f396618749e6cd268015a6a76566_r.jpg"></noscript><img src="data:image/svg+xml;utf8,&lt;svg%20xmlns='http://www.w3.org/2000/svg'%20width='1074'%20height='1497'&gt;&lt;/svg&gt;" data-caption="" data-size="normal" data-rawwidth="1074" data-rawheight="1497" class="origin_image zh-lightbox-thumb lazy" width="1074" data-original="https://pic1.zhimg.com/v2-99c2f396618749e6cd268015a6a76566_r.jpg" data-actualsrc="https://pic1.zhimg.com/v2-99c2f396618749e6cd268015a6a76566_b.jpg"></figure><p>每次参与闪聘活动的企业方均为一线互联网公司，如阿里巴巴、腾讯、美团点评等。如果你对这些职位感兴趣，可以先和我们进行交流，我们会通过社群里的人际关系链帮助大家与团队负责人更好地建立连接。</p><p>想找工作的朋友，可以先将简历发送到</p><p><br></p><p><b>wang.xin@izhiqun.com</b></p><p><br></p><p>【知群】收到简历后，会先和大家接触，了解大家的求职意向、沟通职位具体要求等，让我们一起把知群的内推服务做得更好。或者可以添加助手Belle的微信，微信号：Belle-Pearly，具体沟通。</p>
		val state: String, //published
		val annotation_action: List<Any>,
		val sourceUrl: String,
		val pageCommentsCount: Int, //1
		val canComment: Boolean, //false
		val has_publishing_draft: Boolean, //false
		val snapshotUrl: String,
		val slug: Int, //33717055
		val publishedTime: String, //2018-02-09T12:13:22+08:00
		val url: String, ///p/33717055
		val title: String, //年后想跳槽？我们可以帮你直接内推至阿里巴巴、腾讯、华为……
		val lastestLikers: List<LastestLiker>,
		val summary: String, //<img src="https://pic1.zhimg.com/v2-99c2f396618749e6cd268015a6a76566_200x112.jpg" data-caption="" data-size="normal" data-rawwidth="1074" data-rawheight="1497" class="origin_image inline-img zh-lightbox-thumb" data-original="https://pic1.zhimg.com/v2-99c2f396618749e6cd268015a6a76566_r.jpg">【知群】是做“连接”的知识社群产品，连接学习者和“分享/招聘者”。在过去的半年时间里，【知群】以学习社群的方式与几十万互联网从业人员建立了连接，这其中包括了近千名资深专家、总监、副总裁以及一线实战的创始人所在的互联网学习圈，以及以UI和UX为…
		val reviewingCommentsCount: Int, //0
		val meta: Meta,
		val annotation_detail: Any, //null
		val commentPermission: String, //anyone
		val commentsCount: Int, //1
		val likesCount: Int //25
) {
	data class LastestLiker(
			val bio: String, //设计师
			val isFollowing: Boolean, //false
			val hash: String, //bc27a79a243ad1bc23d3619275acfbd0
			val uid: Long, //663377478475845632
			val isOrg: Boolean, //false
			val slug: String, //gang-tie-di
			val isFollowed: Boolean, //false
			val description: String,
			val name: String, //钢铁迪
			val profileUrl: String, //https://www.zhihu.com/people/gang-tie-di
			val avatar: Avatar,
			val isOrgWhiteList: Boolean, //false
			val isBanned: Boolean //false
	) {
		data class Avatar(
				val id: String, //9dcc7c682f39e3cf97a504bccf74c632
				val template: String //https://pic3.zhimg.com/{id}_{size}.jpg
		)
	}

	data class TitleImageSize(
			val width: Int, //1920
			val height: Int //1285
	)

	data class Links(
			val comments: String ///api/posts/33717055/comments
	)

	data class Meta(
			val previous: Previous,
			val next: Any //null
	) {
		data class Previous(
				val isTitleImageFullScreen: Boolean, //false
				val rating: String, //none
				val titleImage: String,
				val links: Links,
				val topics: List<Topic>,
				val admin_closed_comment: Boolean, //false
				val href: String, ///api/posts/33716448
				val excerptTitle: String,
				val author: Author,
				val content: String, //<p>我们最近在和一些面向设计师、产品经理的工具软件团队合作，觉得有一些很有意思的模式，希望能够和更多的团队一起合作。</p><p>如果有这个方面相关的开发者，产品在国内拥有相当用户量的，欢迎联系我。</p><p>我们覆盖了非常大的设计师、产品经理用户群体，在帮助更多的人在专业方面成长，同时也希望能够帮到更多致力于做这个领域工具产品、为专业人群服务的团队。</p><p>直接在知乎私信我就可以。</p><p></p>
				val state: String, //published
				val sourceUrl: String,
				val pageCommentsCount: Int, //0
				val canComment: Boolean, //false
				val snapshotUrl: String,
				val slug: Int, //33716448
				val publishedTime: String, //2018-02-09T11:52:11+08:00
				val url: String, ///p/33716448
				val title: String, //寻找面向设计师、产品经理的工具软件、App 合作
				val summary: String, //我们最近在和一些面向设计师、产品经理的工具软件团队合作，觉得有一些很有意思的模式，希望能够和更多的团队一起合作。如果有这个方面相关的开发者，产品在国内拥有相当用户量的，欢迎联系我。我们覆盖了非常大的设计师、产品经理用户群体，在帮助更多的人…
				val reviewingCommentsCount: Int, //0
				val meta: Meta,
				val commentPermission: String, //anyone
				val commentsCount: Int, //3
				val likesCount: Int //21
		) {
			data class Links(
					val comments: String ///api/posts/33716448/comments
			)

			data class Topic(
					val url: String, //https://www.zhihu.com/topic/19551325
					val id: String, //19551325
					val name: String //产品经理
			)

			data class Author(
					val bio: String, //马力在招聘：zhuanlan.zhihu.com/p/31904197
					val isFollowing: Boolean, //false
					val hash: String, //c6e85ba5d5999df4c5ce2f2903b1ce0e
					val uid: Long, //26680571723776
					val isOrg: Boolean, //false
					val slug: String, //mali
					val isFollowed: Boolean, //false
					val description: String, //最美应用创始人 产品经理 设计师，欢迎关注微博：@Ma_Li | 他在好奇的注视着这个饶有趣味的世界 ｜ 感谢在评选知乎年度荣誉会员时为我投票的各位，一起认真！| 马力的互联网学习圈：http://mali.brixd.com     |  文章索引： https://zhuanlan.zhihu.com/p/25493627
					val name: String, //马力
					val profileUrl: String, //https://www.zhihu.com/people/mali
					val avatar: Avatar,
					val isOrgWhiteList: Boolean, //false
					val isBanned: Boolean //false
			) {
				data class Avatar(
						val id: String, //ba332a401
						val template: String //https://pic2.zhimg.com/{id}_{size}.jpg
				)
			}

			data class Meta(
					val previous: Any, //null
					val next: Any //null
			)
		}
	}

	data class Author(
			val bio: String, //无法定义
			val isFollowing: Boolean, //false
			val hash: String, //0ddb38d93b1c1d6c91cfb94a1d46ef99
			val uid: Long, //33147160887296
			val isOrg: Boolean, //false
			val slug: String, //christinehu
			val isFollowed: Boolean, //false
			val description: String, //好好活下去……每天都有新打击
			val name: String, //夏漱香菜
			val badge: Badge,
			val profileUrl: String, //https://www.zhihu.com/people/christinehu
			val avatar: Avatar,
			val isOrgWhiteList: Boolean, //false
			val isBanned: Boolean //false
	) {
		data class Avatar(
				val id: String, //v2-0efada2f1686480f2163224252b8f824
				val template: String //https://pic3.zhimg.com/{id}_{size}.jpg
		)

		data class Badge(
				val identity: Any, //null
				val best_answerer: Any //null
		)
	}

	data class Topic(
			val url: String, //https://www.zhihu.com/topic/19551771
			val id: String, //19551771
			val name: String //求职
	)

	data class Column(
			val slug: String, //design
			val name: String //可能性 | 产品与大设计
	)
}