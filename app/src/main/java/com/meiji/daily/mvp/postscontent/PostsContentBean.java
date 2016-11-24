package com.meiji.daily.mvp.postscontent;

import java.util.List;

/**
 * Created by Meiji on 2016/11/23.
 */

public class PostsContentBean {

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
     * content : <p>那天一个读者在线找我，上来就跟我谈技术。</p><p>她：老师，我们全站启用 HTTPS 了，你觉得怎么样？</p><p>我：没什么啊，不过你们是做什么的？</p><p>她：我们是做网络直播的。</p><p>我：… 直播网站启用全站 HTTPS ？有必要吗？你们是哪一个网站？</p><p>她：斗鱼。斗鱼是目前国内首家，也是目前唯一全站升级到 HTTPS 的网络直播平台。</p><br><p>我不是网络直播的目标用户，不过，一个视频网站启用了全站 HTTPS ，说明还是有技术追求的，这个事情也是有技术含量的，可以聊一下。</p><p>作为一个不懂技术，尤其是「不写代码」的离任 CTO，跟人家讨论技术是挺难为情的事儿。万一露馅了怎么办？所以，有不对的地方，欢迎指出。</p><p>HTTPS 这事儿，国内不少网站也就是用来保护一下用户数据，用户登录的时候启用而已。更多时候，出于性能开销上的折衷考虑，基本上都是非加密传输数据。多媒体文件，尤其是流媒体，明文传输似乎不会出现什么安全问题，然而，实际上也会产生非常严重的后果，比如被恶意劫持流量，页面上动不动插入一个小广告。而访问者还以为这是受访站提供的广告。如果是正常的广告还可以忍，然而色情赌博虚假医疗广告比比皆是。这当然不是好的用户体验。</p><p>所以，在我们这个特定环境下，重视 HTTPS 是应该的，更不用说出于保护用户隐私等目的而去部署 HTTPS 了。</p><p>其实，作为一个技术团队负责人(CTO)，今年至少有几个关于 HTTPS 的标志性事件应该引起你的关注（要不可真不太合格）：</p><ol><li>YouTube 启用 HTTPS 加密，全站 97% 的流量均通过 HTTPS 传输(还有 3% 是因为要兼容旧设备)。</li><li>Netflix 宣布将使用 HTTPS 加密流视频。流视频的加密传输是个技术难题，但 Netflix 无疑找到了一个平衡。</li><li>苹果公司在 WWDC 2016 宣布，到 2017 年 1 月 1 日 App Store 中的所有应用都必须启用 App Transport Security 安全功能。App Transport Security（ATS）是苹果在 iOS 9 中引入的一项隐私保护功能，屏蔽明文 HTTP 资源加载，连接必须经过 HTTPS。</li></ol><p>从全球范围看，推动 HTTPS 技术的公司里，Google 最为积极，专门做了一个专题页面监控「各大热门网站的 HTTPS 实施情况」，而 Google 全站到目前已经有超过 85% 的链接启用了加密。对比之下，中国的互联网公司对这方面的重视程度并不够。所以，这位斗鱼的朋友带着一点自豪感来跟我讲这个事儿是有一定缘由的。</p><p><b>重视 HTTPS 是一种义务和责任</b>。</p><p>见微知著，一个公司如果能在技术层面比别人做得更进一步，愿意做投入，去做一些改变用户体验的事情，其他地方想必也会花心思，据斗鱼介绍，一个直播房间已经可以支撑百万人同时发弹幕。单从技术的角度讲，用户对弹幕系统的要求其实也挺高的，需要能支撑高并发、实时性的系统，做起来同样存在难度和挑战。<br></p><p>一个有追求的技术人在这样的团队里会遇到技术上的挑战，当然工作成果也会得到重视。</p><p>这也是我写这篇文章的主要原因。毫无疑问，本文由斗鱼直播平台约稿。</p>
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
     * meta : {"previous":{"isTitleImageFullScreen":false,"rating":"none","titleImage":"https://pic1.zhimg.com/v2-ab3181c294cd0558a729d6a7d3b53940_r.jpg","links":{"comments":"/api/posts/23342653/comments"},"topics":[{"url":"https://www.zhihu.com/topic/19569604","id":"19569604","name":"个人所得税"}],"href":"/api/posts/23342653","excerptTitle":"","author":{"profileUrl":"https://www.zhihu.com/people/fenng","bio":"「小道消息」，微信号：WebNotes","hash":"99953853cc4219fabe8327301058357c","uid":26676083818496,"isOrg":false,"description":"手艺人.\n知乎74号用户.\n微信公众帐号「小道消息」, 微信号: 「WebNotes」\n\n黑我的人最后都变成了我的粉，需要新的黑子。","isOrgWhiteList":false,"slug":"fenng","avatar":{"id":"9ee82f3f07623303aa42164b523ac267","template":"https://pic4.zhimg.com/{id}_{size}.jpg"},"name":"Fenng"},"column":{"slug":"WebNotes","name":"小道消息"},"content":"<p>前几天热议「年收入 12 万可能要加税」的话题，看了不少人转发相关文章，朋友圈都被刷屏了，很容易产生一种遍地「高收入」群体的错觉。不过，怕是很少有人思考这个基本问题：全国年收入超过 12 万的纳税人到底有多少？<\/p><p>在朋友圈做了一个小调查，绝大多数人的反馈挺让我惊讶，大家都太乐观了。<br><\/p><p>全国年收入超过 12 万的纳税人没有你想像的那么多。比大多数人直觉上预估的人数要少得多。<br><\/p><p>先摘录一组公开数据：<\/p><ol><li>厦门，2015 年 12 万元以上个人所得税自行纳税申报的有 8.6 万人（包括 4614 名外国人）。厦门 2015 年申报个人所得税有 412.4 万人，其中，年收入超过 12 万元的有 8.6 万人，占申报个税总人数的 2.1%。数据来源：海西晨报。<\/li><li>苏州，2015 年度年所得 12 万元以上个人所得税自行纳税申报人数达 217837 人。数据来源：新华日报。另据苏州日报的数据是 16 万人，这里取最高值。<\/li><li>天津，2014 年度年所得 12 万元以上自行纳税申报人数超 16 万。数据来源：每日新报。<\/li><li>武汉，2014 年度年入 12 万以上者超 8.6 万人。数据来源：湖北日报。<br><\/li><li>安徽，2014 年度 「年收入超过 12 万元」自行申报个税人数达 77256 人。数据来源：新安晚报。<\/li><li>辽宁省 2015 年度年收入 12 万元以上个人申报的总人数为 106884 人(不包括大连)。数据来源：辽宁日报。<\/li><li>河南省 2015 年共受理年所得 12 万元以上纳税人自行纳税申报 141748 人。数据来源：河南日报。<\/li><li>江西，2014 年度年所得 12 万元以上个税自行纳税申报全省共 65576 人。数据来源：中国江西网-信息日报。<\/li><li>西藏，2014 年度年所得 12 万元个税自行申报首次突破万人。数据来源：中国西藏新闻网。<\/li><\/ol><p>根据这些数据，我们可以得到一个大致推断，年所得 12 万以上的纳税人没那么多，而一些人口大省，年所得 12 万以上的纳税人还不如一个经济发达的地级城市多。<\/p><p>我们再继续看公开数据，2010 年我国个人所得税约 4837 亿元，2011 年我国个人所得税收入 6054 亿元，2012 年我国个人所得税实现收入 5820.24 亿元(略有下降的原因是从 2011 年 9 月 1 日起，个人所得税的起征点调整为 3500 元)，2014 年个人所得税税收总共为 7,376.57 亿元，2015 年的个人所得税为 8618 亿元。<\/p><p>财新网报道，2010 年中国所得 12 万元纳税申报人数为 315 万，比 2009 年增加 46 万多人。2006 年，申报人数不到 163 万，该年度这个群体人均缴税额 49733 元。<br><\/p><p>在个税起征点调整之前，「2008 年年所得 12 万元以上纳税人自行申报的人数为 240 万人，占全国个人所得税纳税人数的约 3％，而缴纳的税额为 1294 亿元，占全国个人所得税总收入的 35%。」数据来源：中国政府网。从这个数据推算，<b> 2008 年，缴纳个税总人数约为 8000 万人<\/b>。<br><\/p><p>个税起征点调高之后呢？全国政协委员、财政部财政科学研究所原所长贾康在 2015 年接受京华时报的采访中说：「现在交个税的人很少，只有 2800 万人，占整个人口总数的不到 2%」。<br><\/p><p>另据中国经营报的一篇文章称：「2011 年 9 月 1 日之后，工薪所得税费用减除标准提高到 3500 元，而个税法修改后，纳税人数由约 8400 万人减至约 2400 万人。」<br><\/p><p>注意，这两个数据并不矛盾。因为年度不同，随着经济增长，个税法修改，缴纳个税人数从 2400 万增加到 2800 万人是正常的。<br><\/p><p>从以上这些数据大致估算一下，2015 年<b>年收入超过 12 万的纳税申报人总数 550 万左右，误差正负 50 万<\/b>，因为最近两年经济波动实在是大。最低值 500 万，人均 5 万年缴税额(参考 2006 年该群体人均缴税额 49733 元)，缴税 2500 亿；最高值 600 万，人均 5 万年缴税额，缴税 3000 亿。3000 亿的话，对比去年总税额 8618 亿，差不多是 35%，份额跟 2008 年的 35% 大约持平。当然，如果你是个认真的人，可以根据上面的数据做个模型，再算一下，我只是估算，毕竟缺乏最新的有效数据支撑。<br><\/p><p>必须说明的是，以上结论并非十分严谨，误差不小。而且，至少应该考虑可能存在的问题是，人均 5 万的纳税额现在是否已经有变化？<br><\/p><p>可以得到的结论是，「年收入 12 万可能要加税」应该是误解(随后也有官方辟谣)，对这几百万人加税，意义不大。因为全国年所得超过 12 万的肯定不只是申报的这几百万人，这也是我们必须要强调的一点。<br><\/p><p>一个业余选手研究一下数据，希望能给你一点小小的启发。<\/p><p>题图：Self-portrait with Bandaged Ear by Vincent van Gogh<br><\/p><br><p>讨论点击<a href=\"https://link.zhihu.com/?target=https%3A//wx.xiaomiquan.com/mweb/views/joingroup/join_group.html%3Fgroup_id%3D728151117%26secret%3D56ipgdwr39q94572gxaf3khlcc208rdc%26extra%3D488fc61f3913e4530a252c9bb11c09b70ee93f176ce80fd8aa1ef45753cb71d4\" class=\" wrap external\" target=\"_blank\" rel=\"nofollow noreferrer\">这里<i class=\"icon-external\"><\/i><\/a><\/p>","state":"published","sourceUrl":"","pageCommentsCount":0,"canComment":false,"snapshotUrl":"","slug":23342653,"publishedTime":"2016-11-01T11:31:52+08:00","url":"/p/23342653","title":"全国年收入超过 12 万的纳税人有多少？","summary":"前几天热议「年收入 12 万可能要加税」的话题，看了不少人转发相关文章，朋友圈都被刷屏了，很容易产生一种遍地「高收入」群体的错觉。不过，怕是很少有人思考这个基本问题：全国年收入超过 12 万的纳税人到底有多少？在朋友圈做了一个小调查，绝大多数人的\u2026","reviewingCommentsCount":0,"meta":{"previous":null,"next":null},"commentPermission":"friends","commentsCount":0,"likesCount":0},"next":null}
     * commentPermission : friends
     * commentsCount : 2
     * likesCount : 84
     */

    private boolean isTitleImageFullScreen;
    private String rating;
    private String titleImage;
    private LinksBean links;
    private TitleImageSizeBean titleImageSize;
    private String href;
    private String excerptTitle;
    private AuthorBean author;
    private ColumnBean column;
    private String tipjarState;
    private String content;
    private String state;
    private String sourceUrl;
    private int pageCommentsCount;
    private boolean canComment;
    private String snapshotUrl;
    private int slug;
    private String publishedTime;
    private String url;
    private String title;
    private String summary;
    private int reviewingCommentsCount;
    private MetaBeanX meta;
    private String commentPermission;
    private int commentsCount;
    private int likesCount;
    private List<?> reviewers;
    private List<TopicsBeanX> topics;
    private List<LastestLikersBean> lastestLikers;

    public boolean isIsTitleImageFullScreen() {
        return isTitleImageFullScreen;
    }

    public void setIsTitleImageFullScreen(boolean isTitleImageFullScreen) {
        this.isTitleImageFullScreen = isTitleImageFullScreen;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getTitleImage() {
        return titleImage;
    }

    public void setTitleImage(String titleImage) {
        this.titleImage = titleImage;
    }

    public LinksBean getLinks() {
        return links;
    }

    public void setLinks(LinksBean links) {
        this.links = links;
    }

    public TitleImageSizeBean getTitleImageSize() {
        return titleImageSize;
    }

    public void setTitleImageSize(TitleImageSizeBean titleImageSize) {
        this.titleImageSize = titleImageSize;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getExcerptTitle() {
        return excerptTitle;
    }

    public void setExcerptTitle(String excerptTitle) {
        this.excerptTitle = excerptTitle;
    }

    public AuthorBean getAuthor() {
        return author;
    }

    public void setAuthor(AuthorBean author) {
        this.author = author;
    }

    public ColumnBean getColumn() {
        return column;
    }

    public void setColumn(ColumnBean column) {
        this.column = column;
    }

    public String getTipjarState() {
        return tipjarState;
    }

    public void setTipjarState(String tipjarState) {
        this.tipjarState = tipjarState;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public int getPageCommentsCount() {
        return pageCommentsCount;
    }

    public void setPageCommentsCount(int pageCommentsCount) {
        this.pageCommentsCount = pageCommentsCount;
    }

    public boolean isCanComment() {
        return canComment;
    }

    public void setCanComment(boolean canComment) {
        this.canComment = canComment;
    }

    public String getSnapshotUrl() {
        return snapshotUrl;
    }

    public void setSnapshotUrl(String snapshotUrl) {
        this.snapshotUrl = snapshotUrl;
    }

    public int getSlug() {
        return slug;
    }

    public void setSlug(int slug) {
        this.slug = slug;
    }

    public String getPublishedTime() {
        return publishedTime;
    }

    public void setPublishedTime(String publishedTime) {
        this.publishedTime = publishedTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public int getReviewingCommentsCount() {
        return reviewingCommentsCount;
    }

    public void setReviewingCommentsCount(int reviewingCommentsCount) {
        this.reviewingCommentsCount = reviewingCommentsCount;
    }

    public MetaBeanX getMeta() {
        return meta;
    }

    public void setMeta(MetaBeanX meta) {
        this.meta = meta;
    }

    public String getCommentPermission() {
        return commentPermission;
    }

    public void setCommentPermission(String commentPermission) {
        this.commentPermission = commentPermission;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public List<?> getReviewers() {
        return reviewers;
    }

    public void setReviewers(List<?> reviewers) {
        this.reviewers = reviewers;
    }

    public List<TopicsBeanX> getTopics() {
        return topics;
    }

    public void setTopics(List<TopicsBeanX> topics) {
        this.topics = topics;
    }

    public List<LastestLikersBean> getLastestLikers() {
        return lastestLikers;
    }

    public void setLastestLikers(List<LastestLikersBean> lastestLikers) {
        this.lastestLikers = lastestLikers;
    }

    public static class LinksBean {
        /**
         * comments : /api/posts/23480182/comments
         */

        private String comments;

        public String getComments() {
            return comments;
        }

        public void setComments(String comments) {
            this.comments = comments;
        }
    }

    public static class TitleImageSizeBean {
        /**
         * width : 0
         * height : 0
         */

        private int width;
        private int height;

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }
    }

    public static class AuthorBean {
        /**
         * bio : 「小道消息」，微信号：WebNotes
         * hash : 99953853cc4219fabe8327301058357c
         * uid : 26676083818496
         * isOrg : false
         * slug : fenng
         * description : 手艺人.
         * 知乎74号用户.
         * 微信公众帐号「小道消息」, 微信号: 「WebNotes」
         * <p>
         * 黑我的人最后都变成了我的粉，需要新的黑子。
         * name : Fenng
         * profileUrl : https://www.zhihu.com/people/fenng
         * avatar : {"id":"9ee82f3f07623303aa42164b523ac267","template":"https://pic4.zhimg.com/{id}_{size}.jpg"}
         * isOrgWhiteList : false
         * badge : {"identity":null,"best_answerer":{"topics":[],"description":"优秀回答者"}}
         */

        private String bio;
        private String hash;
        private long uid;
        private boolean isOrg;
        private String slug;
        private String description;
        private String name;
        private String profileUrl;
        private AvatarBean avatar;
        private boolean isOrgWhiteList;
        private BadgeBean badge;

        public String getBio() {
            return bio;
        }

        public void setBio(String bio) {
            this.bio = bio;
        }

        public String getHash() {
            return hash;
        }

        public void setHash(String hash) {
            this.hash = hash;
        }

        public long getUid() {
            return uid;
        }

        public void setUid(long uid) {
            this.uid = uid;
        }

        public boolean isIsOrg() {
            return isOrg;
        }

        public void setIsOrg(boolean isOrg) {
            this.isOrg = isOrg;
        }

        public String getSlug() {
            return slug;
        }

        public void setSlug(String slug) {
            this.slug = slug;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getProfileUrl() {
            return profileUrl;
        }

        public void setProfileUrl(String profileUrl) {
            this.profileUrl = profileUrl;
        }

        public AvatarBean getAvatar() {
            return avatar;
        }

        public void setAvatar(AvatarBean avatar) {
            this.avatar = avatar;
        }

        public boolean isIsOrgWhiteList() {
            return isOrgWhiteList;
        }

        public void setIsOrgWhiteList(boolean isOrgWhiteList) {
            this.isOrgWhiteList = isOrgWhiteList;
        }

        public BadgeBean getBadge() {
            return badge;
        }

        public void setBadge(BadgeBean badge) {
            this.badge = badge;
        }

        public static class AvatarBean {
            /**
             * id : 9ee82f3f07623303aa42164b523ac267
             * template : https://pic4.zhimg.com/{id}_{size}.jpg
             */

            private String id;
            private String template;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getTemplate() {
                return template;
            }

            public void setTemplate(String template) {
                this.template = template;
            }
        }

        public static class BadgeBean {
            /**
             * identity : null
             * best_answerer : {"topics":[],"description":"优秀回答者"}
             */

            private Object identity;
            private BestAnswererBean best_answerer;

            public Object getIdentity() {
                return identity;
            }

            public void setIdentity(Object identity) {
                this.identity = identity;
            }

            public BestAnswererBean getBest_answerer() {
                return best_answerer;
            }

            public void setBest_answerer(BestAnswererBean best_answerer) {
                this.best_answerer = best_answerer;
            }

            public static class BestAnswererBean {
                /**
                 * topics : []
                 * description : 优秀回答者
                 */

                private String description;
                private List<?> topics;

                public String getDescription() {
                    return description;
                }

                public void setDescription(String description) {
                    this.description = description;
                }

                public List<?> getTopics() {
                    return topics;
                }

                public void setTopics(List<?> topics) {
                    this.topics = topics;
                }
            }
        }
    }

    public static class ColumnBean {
        /**
         * slug : WebNotes
         * name : 小道消息
         */

        private String slug;
        private String name;

        public String getSlug() {
            return slug;
        }

        public void setSlug(String slug) {
            this.slug = slug;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class MetaBeanX {
        /**
         * previous : {"isTitleImageFullScreen":false,"rating":"none","titleImage":"https://pic1.zhimg.com/v2-ab3181c294cd0558a729d6a7d3b53940_r.jpg","links":{"comments":"/api/posts/23342653/comments"},"topics":[{"url":"https://www.zhihu.com/topic/19569604","id":"19569604","name":"个人所得税"}],"href":"/api/posts/23342653","excerptTitle":"","author":{"profileUrl":"https://www.zhihu.com/people/fenng","bio":"「小道消息」，微信号：WebNotes","hash":"99953853cc4219fabe8327301058357c","uid":26676083818496,"isOrg":false,"description":"手艺人.\n知乎74号用户.\n微信公众帐号「小道消息」, 微信号: 「WebNotes」\n\n黑我的人最后都变成了我的粉，需要新的黑子。","isOrgWhiteList":false,"slug":"fenng","avatar":{"id":"9ee82f3f07623303aa42164b523ac267","template":"https://pic4.zhimg.com/{id}_{size}.jpg"},"name":"Fenng"},"column":{"slug":"WebNotes","name":"小道消息"},"content":"<p>前几天热议「年收入 12 万可能要加税」的话题，看了不少人转发相关文章，朋友圈都被刷屏了，很容易产生一种遍地「高收入」群体的错觉。不过，怕是很少有人思考这个基本问题：全国年收入超过 12 万的纳税人到底有多少？<\/p><p>在朋友圈做了一个小调查，绝大多数人的反馈挺让我惊讶，大家都太乐观了。<br><\/p><p>全国年收入超过 12 万的纳税人没有你想像的那么多。比大多数人直觉上预估的人数要少得多。<br><\/p><p>先摘录一组公开数据：<\/p><ol><li>厦门，2015 年 12 万元以上个人所得税自行纳税申报的有 8.6 万人（包括 4614 名外国人）。厦门 2015 年申报个人所得税有 412.4 万人，其中，年收入超过 12 万元的有 8.6 万人，占申报个税总人数的 2.1%。数据来源：海西晨报。<\/li><li>苏州，2015 年度年所得 12 万元以上个人所得税自行纳税申报人数达 217837 人。数据来源：新华日报。另据苏州日报的数据是 16 万人，这里取最高值。<\/li><li>天津，2014 年度年所得 12 万元以上自行纳税申报人数超 16 万。数据来源：每日新报。<\/li><li>武汉，2014 年度年入 12 万以上者超 8.6 万人。数据来源：湖北日报。<br><\/li><li>安徽，2014 年度 「年收入超过 12 万元」自行申报个税人数达 77256 人。数据来源：新安晚报。<\/li><li>辽宁省 2015 年度年收入 12 万元以上个人申报的总人数为 106884 人(不包括大连)。数据来源：辽宁日报。<\/li><li>河南省 2015 年共受理年所得 12 万元以上纳税人自行纳税申报 141748 人。数据来源：河南日报。<\/li><li>江西，2014 年度年所得 12 万元以上个税自行纳税申报全省共 65576 人。数据来源：中国江西网-信息日报。<\/li><li>西藏，2014 年度年所得 12 万元个税自行申报首次突破万人。数据来源：中国西藏新闻网。<\/li><\/ol><p>根据这些数据，我们可以得到一个大致推断，年所得 12 万以上的纳税人没那么多，而一些人口大省，年所得 12 万以上的纳税人还不如一个经济发达的地级城市多。<\/p><p>我们再继续看公开数据，2010 年我国个人所得税约 4837 亿元，2011 年我国个人所得税收入 6054 亿元，2012 年我国个人所得税实现收入 5820.24 亿元(略有下降的原因是从 2011 年 9 月 1 日起，个人所得税的起征点调整为 3500 元)，2014 年个人所得税税收总共为 7,376.57 亿元，2015 年的个人所得税为 8618 亿元。<\/p><p>财新网报道，2010 年中国所得 12 万元纳税申报人数为 315 万，比 2009 年增加 46 万多人。2006 年，申报人数不到 163 万，该年度这个群体人均缴税额 49733 元。<br><\/p><p>在个税起征点调整之前，「2008 年年所得 12 万元以上纳税人自行申报的人数为 240 万人，占全国个人所得税纳税人数的约 3％，而缴纳的税额为 1294 亿元，占全国个人所得税总收入的 35%。」数据来源：中国政府网。从这个数据推算，<b> 2008 年，缴纳个税总人数约为 8000 万人<\/b>。<br><\/p><p>个税起征点调高之后呢？全国政协委员、财政部财政科学研究所原所长贾康在 2015 年接受京华时报的采访中说：「现在交个税的人很少，只有 2800 万人，占整个人口总数的不到 2%」。<br><\/p><p>另据中国经营报的一篇文章称：「2011 年 9 月 1 日之后，工薪所得税费用减除标准提高到 3500 元，而个税法修改后，纳税人数由约 8400 万人减至约 2400 万人。」<br><\/p><p>注意，这两个数据并不矛盾。因为年度不同，随着经济增长，个税法修改，缴纳个税人数从 2400 万增加到 2800 万人是正常的。<br><\/p><p>从以上这些数据大致估算一下，2015 年<b>年收入超过 12 万的纳税申报人总数 550 万左右，误差正负 50 万<\/b>，因为最近两年经济波动实在是大。最低值 500 万，人均 5 万年缴税额(参考 2006 年该群体人均缴税额 49733 元)，缴税 2500 亿；最高值 600 万，人均 5 万年缴税额，缴税 3000 亿。3000 亿的话，对比去年总税额 8618 亿，差不多是 35%，份额跟 2008 年的 35% 大约持平。当然，如果你是个认真的人，可以根据上面的数据做个模型，再算一下，我只是估算，毕竟缺乏最新的有效数据支撑。<br><\/p><p>必须说明的是，以上结论并非十分严谨，误差不小。而且，至少应该考虑可能存在的问题是，人均 5 万的纳税额现在是否已经有变化？<br><\/p><p>可以得到的结论是，「年收入 12 万可能要加税」应该是误解(随后也有官方辟谣)，对这几百万人加税，意义不大。因为全国年所得超过 12 万的肯定不只是申报的这几百万人，这也是我们必须要强调的一点。<br><\/p><p>一个业余选手研究一下数据，希望能给你一点小小的启发。<\/p><p>题图：Self-portrait with Bandaged Ear by Vincent van Gogh<br><\/p><br><p>讨论点击<a href=\"https://link.zhihu.com/?target=https%3A//wx.xiaomiquan.com/mweb/views/joingroup/join_group.html%3Fgroup_id%3D728151117%26secret%3D56ipgdwr39q94572gxaf3khlcc208rdc%26extra%3D488fc61f3913e4530a252c9bb11c09b70ee93f176ce80fd8aa1ef45753cb71d4\" class=\" wrap external\" target=\"_blank\" rel=\"nofollow noreferrer\">这里<i class=\"icon-external\"><\/i><\/a><\/p>","state":"published","sourceUrl":"","pageCommentsCount":0,"canComment":false,"snapshotUrl":"","slug":23342653,"publishedTime":"2016-11-01T11:31:52+08:00","url":"/p/23342653","title":"全国年收入超过 12 万的纳税人有多少？","summary":"前几天热议「年收入 12 万可能要加税」的话题，看了不少人转发相关文章，朋友圈都被刷屏了，很容易产生一种遍地「高收入」群体的错觉。不过，怕是很少有人思考这个基本问题：全国年收入超过 12 万的纳税人到底有多少？在朋友圈做了一个小调查，绝大多数人的\u2026","reviewingCommentsCount":0,"meta":{"previous":null,"next":null},"commentPermission":"friends","commentsCount":0,"likesCount":0}
         * next : null
         */

        private PreviousBean previous;
        private Object next;

        public PreviousBean getPrevious() {
            return previous;
        }

        public void setPrevious(PreviousBean previous) {
            this.previous = previous;
        }

        public Object getNext() {
            return next;
        }

        public void setNext(Object next) {
            this.next = next;
        }

        public static class PreviousBean {
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
             * content : <p>前几天热议「年收入 12 万可能要加税」的话题，看了不少人转发相关文章，朋友圈都被刷屏了，很容易产生一种遍地「高收入」群体的错觉。不过，怕是很少有人思考这个基本问题：全国年收入超过 12 万的纳税人到底有多少？</p><p>在朋友圈做了一个小调查，绝大多数人的反馈挺让我惊讶，大家都太乐观了。<br></p><p>全国年收入超过 12 万的纳税人没有你想像的那么多。比大多数人直觉上预估的人数要少得多。<br></p><p>先摘录一组公开数据：</p><ol><li>厦门，2015 年 12 万元以上个人所得税自行纳税申报的有 8.6 万人（包括 4614 名外国人）。厦门 2015 年申报个人所得税有 412.4 万人，其中，年收入超过 12 万元的有 8.6 万人，占申报个税总人数的 2.1%。数据来源：海西晨报。</li><li>苏州，2015 年度年所得 12 万元以上个人所得税自行纳税申报人数达 217837 人。数据来源：新华日报。另据苏州日报的数据是 16 万人，这里取最高值。</li><li>天津，2014 年度年所得 12 万元以上自行纳税申报人数超 16 万。数据来源：每日新报。</li><li>武汉，2014 年度年入 12 万以上者超 8.6 万人。数据来源：湖北日报。<br></li><li>安徽，2014 年度 「年收入超过 12 万元」自行申报个税人数达 77256 人。数据来源：新安晚报。</li><li>辽宁省 2015 年度年收入 12 万元以上个人申报的总人数为 106884 人(不包括大连)。数据来源：辽宁日报。</li><li>河南省 2015 年共受理年所得 12 万元以上纳税人自行纳税申报 141748 人。数据来源：河南日报。</li><li>江西，2014 年度年所得 12 万元以上个税自行纳税申报全省共 65576 人。数据来源：中国江西网-信息日报。</li><li>西藏，2014 年度年所得 12 万元个税自行申报首次突破万人。数据来源：中国西藏新闻网。</li></ol><p>根据这些数据，我们可以得到一个大致推断，年所得 12 万以上的纳税人没那么多，而一些人口大省，年所得 12 万以上的纳税人还不如一个经济发达的地级城市多。</p><p>我们再继续看公开数据，2010 年我国个人所得税约 4837 亿元，2011 年我国个人所得税收入 6054 亿元，2012 年我国个人所得税实现收入 5820.24 亿元(略有下降的原因是从 2011 年 9 月 1 日起，个人所得税的起征点调整为 3500 元)，2014 年个人所得税税收总共为 7,376.57 亿元，2015 年的个人所得税为 8618 亿元。</p><p>财新网报道，2010 年中国所得 12 万元纳税申报人数为 315 万，比 2009 年增加 46 万多人。2006 年，申报人数不到 163 万，该年度这个群体人均缴税额 49733 元。<br></p><p>在个税起征点调整之前，「2008 年年所得 12 万元以上纳税人自行申报的人数为 240 万人，占全国个人所得税纳税人数的约 3％，而缴纳的税额为 1294 亿元，占全国个人所得税总收入的 35%。」数据来源：中国政府网。从这个数据推算，<b> 2008 年，缴纳个税总人数约为 8000 万人</b>。<br></p><p>个税起征点调高之后呢？全国政协委员、财政部财政科学研究所原所长贾康在 2015 年接受京华时报的采访中说：「现在交个税的人很少，只有 2800 万人，占整个人口总数的不到 2%」。<br></p><p>另据中国经营报的一篇文章称：「2011 年 9 月 1 日之后，工薪所得税费用减除标准提高到 3500 元，而个税法修改后，纳税人数由约 8400 万人减至约 2400 万人。」<br></p><p>注意，这两个数据并不矛盾。因为年度不同，随着经济增长，个税法修改，缴纳个税人数从 2400 万增加到 2800 万人是正常的。<br></p><p>从以上这些数据大致估算一下，2015 年<b>年收入超过 12 万的纳税申报人总数 550 万左右，误差正负 50 万</b>，因为最近两年经济波动实在是大。最低值 500 万，人均 5 万年缴税额(参考 2006 年该群体人均缴税额 49733 元)，缴税 2500 亿；最高值 600 万，人均 5 万年缴税额，缴税 3000 亿。3000 亿的话，对比去年总税额 8618 亿，差不多是 35%，份额跟 2008 年的 35% 大约持平。当然，如果你是个认真的人，可以根据上面的数据做个模型，再算一下，我只是估算，毕竟缺乏最新的有效数据支撑。<br></p><p>必须说明的是，以上结论并非十分严谨，误差不小。而且，至少应该考虑可能存在的问题是，人均 5 万的纳税额现在是否已经有变化？<br></p><p>可以得到的结论是，「年收入 12 万可能要加税」应该是误解(随后也有官方辟谣)，对这几百万人加税，意义不大。因为全国年所得超过 12 万的肯定不只是申报的这几百万人，这也是我们必须要强调的一点。<br></p><p>一个业余选手研究一下数据，希望能给你一点小小的启发。</p><p>题图：Self-portrait with Bandaged Ear by Vincent van Gogh<br></p><br><p>讨论点击<a href="https://link.zhihu.com/?target=https%3A//wx.xiaomiquan.com/mweb/views/joingroup/join_group.html%3Fgroup_id%3D728151117%26secret%3D56ipgdwr39q94572gxaf3khlcc208rdc%26extra%3D488fc61f3913e4530a252c9bb11c09b70ee93f176ce80fd8aa1ef45753cb71d4" class=" wrap external" target="_blank" rel="nofollow noreferrer">这里<i class="icon-external"></i></a></p>
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

            private boolean isTitleImageFullScreen;
            private String rating;
            private String titleImage;
            private LinksBeanX links;
            private String href;
            private String excerptTitle;
            private AuthorBeanX author;
            private ColumnBeanX column;
            private String content;
            private String state;
            private String sourceUrl;
            private int pageCommentsCount;
            private boolean canComment;
            private String snapshotUrl;
            private int slug;
            private String publishedTime;
            private String url;
            private String title;
            private String summary;
            private int reviewingCommentsCount;
            private MetaBean meta;
            private String commentPermission;
            private int commentsCount;
            private int likesCount;
            private List<TopicsBean> topics;

            public boolean isIsTitleImageFullScreen() {
                return isTitleImageFullScreen;
            }

            public void setIsTitleImageFullScreen(boolean isTitleImageFullScreen) {
                this.isTitleImageFullScreen = isTitleImageFullScreen;
            }

            public String getRating() {
                return rating;
            }

            public void setRating(String rating) {
                this.rating = rating;
            }

            public String getTitleImage() {
                return titleImage;
            }

            public void setTitleImage(String titleImage) {
                this.titleImage = titleImage;
            }

            public LinksBeanX getLinks() {
                return links;
            }

            public void setLinks(LinksBeanX links) {
                this.links = links;
            }

            public String getHref() {
                return href;
            }

            public void setHref(String href) {
                this.href = href;
            }

            public String getExcerptTitle() {
                return excerptTitle;
            }

            public void setExcerptTitle(String excerptTitle) {
                this.excerptTitle = excerptTitle;
            }

            public AuthorBeanX getAuthor() {
                return author;
            }

            public void setAuthor(AuthorBeanX author) {
                this.author = author;
            }

            public ColumnBeanX getColumn() {
                return column;
            }

            public void setColumn(ColumnBeanX column) {
                this.column = column;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }

            public String getSourceUrl() {
                return sourceUrl;
            }

            public void setSourceUrl(String sourceUrl) {
                this.sourceUrl = sourceUrl;
            }

            public int getPageCommentsCount() {
                return pageCommentsCount;
            }

            public void setPageCommentsCount(int pageCommentsCount) {
                this.pageCommentsCount = pageCommentsCount;
            }

            public boolean isCanComment() {
                return canComment;
            }

            public void setCanComment(boolean canComment) {
                this.canComment = canComment;
            }

            public String getSnapshotUrl() {
                return snapshotUrl;
            }

            public void setSnapshotUrl(String snapshotUrl) {
                this.snapshotUrl = snapshotUrl;
            }

            public int getSlug() {
                return slug;
            }

            public void setSlug(int slug) {
                this.slug = slug;
            }

            public String getPublishedTime() {
                return publishedTime;
            }

            public void setPublishedTime(String publishedTime) {
                this.publishedTime = publishedTime;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getSummary() {
                return summary;
            }

            public void setSummary(String summary) {
                this.summary = summary;
            }

            public int getReviewingCommentsCount() {
                return reviewingCommentsCount;
            }

            public void setReviewingCommentsCount(int reviewingCommentsCount) {
                this.reviewingCommentsCount = reviewingCommentsCount;
            }

            public MetaBean getMeta() {
                return meta;
            }

            public void setMeta(MetaBean meta) {
                this.meta = meta;
            }

            public String getCommentPermission() {
                return commentPermission;
            }

            public void setCommentPermission(String commentPermission) {
                this.commentPermission = commentPermission;
            }

            public int getCommentsCount() {
                return commentsCount;
            }

            public void setCommentsCount(int commentsCount) {
                this.commentsCount = commentsCount;
            }

            public int getLikesCount() {
                return likesCount;
            }

            public void setLikesCount(int likesCount) {
                this.likesCount = likesCount;
            }

            public List<TopicsBean> getTopics() {
                return topics;
            }

            public void setTopics(List<TopicsBean> topics) {
                this.topics = topics;
            }

            public static class LinksBeanX {
                /**
                 * comments : /api/posts/23342653/comments
                 */

                private String comments;

                public String getComments() {
                    return comments;
                }

                public void setComments(String comments) {
                    this.comments = comments;
                }
            }

            public static class AuthorBeanX {
                /**
                 * profileUrl : https://www.zhihu.com/people/fenng
                 * bio : 「小道消息」，微信号：WebNotes
                 * hash : 99953853cc4219fabe8327301058357c
                 * uid : 26676083818496
                 * isOrg : false
                 * description : 手艺人.
                 * 知乎74号用户.
                 * 微信公众帐号「小道消息」, 微信号: 「WebNotes」
                 * <p>
                 * 黑我的人最后都变成了我的粉，需要新的黑子。
                 * isOrgWhiteList : false
                 * slug : fenng
                 * avatar : {"id":"9ee82f3f07623303aa42164b523ac267","template":"https://pic4.zhimg.com/{id}_{size}.jpg"}
                 * name : Fenng
                 */

                private String profileUrl;
                private String bio;
                private String hash;
                private long uid;
                private boolean isOrg;
                private String description;
                private boolean isOrgWhiteList;
                private String slug;
                private AvatarBeanX avatar;
                private String name;

                public String getProfileUrl() {
                    return profileUrl;
                }

                public void setProfileUrl(String profileUrl) {
                    this.profileUrl = profileUrl;
                }

                public String getBio() {
                    return bio;
                }

                public void setBio(String bio) {
                    this.bio = bio;
                }

                public String getHash() {
                    return hash;
                }

                public void setHash(String hash) {
                    this.hash = hash;
                }

                public long getUid() {
                    return uid;
                }

                public void setUid(long uid) {
                    this.uid = uid;
                }

                public boolean isIsOrg() {
                    return isOrg;
                }

                public void setIsOrg(boolean isOrg) {
                    this.isOrg = isOrg;
                }

                public String getDescription() {
                    return description;
                }

                public void setDescription(String description) {
                    this.description = description;
                }

                public boolean isIsOrgWhiteList() {
                    return isOrgWhiteList;
                }

                public void setIsOrgWhiteList(boolean isOrgWhiteList) {
                    this.isOrgWhiteList = isOrgWhiteList;
                }

                public String getSlug() {
                    return slug;
                }

                public void setSlug(String slug) {
                    this.slug = slug;
                }

                public AvatarBeanX getAvatar() {
                    return avatar;
                }

                public void setAvatar(AvatarBeanX avatar) {
                    this.avatar = avatar;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public static class AvatarBeanX {
                    /**
                     * id : 9ee82f3f07623303aa42164b523ac267
                     * template : https://pic4.zhimg.com/{id}_{size}.jpg
                     */

                    private String id;
                    private String template;

                    public String getId() {
                        return id;
                    }

                    public void setId(String id) {
                        this.id = id;
                    }

                    public String getTemplate() {
                        return template;
                    }

                    public void setTemplate(String template) {
                        this.template = template;
                    }
                }
            }

            public static class ColumnBeanX {
                /**
                 * slug : WebNotes
                 * name : 小道消息
                 */

                private String slug;
                private String name;

                public String getSlug() {
                    return slug;
                }

                public void setSlug(String slug) {
                    this.slug = slug;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }
            }

            public static class MetaBean {
                /**
                 * previous : null
                 * next : null
                 */

                private Object previous;
                private Object next;

                public Object getPrevious() {
                    return previous;
                }

                public void setPrevious(Object previous) {
                    this.previous = previous;
                }

                public Object getNext() {
                    return next;
                }

                public void setNext(Object next) {
                    this.next = next;
                }
            }

            public static class TopicsBean {
                /**
                 * url : https://www.zhihu.com/topic/19569604
                 * id : 19569604
                 * name : 个人所得税
                 */

                private String url;
                private String id;
                private String name;

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }
            }
        }
    }

    public static class TopicsBeanX {
        /**
         * url : https://www.zhihu.com/topic/19568367
         * id : 19568367
         * name : HTTPS
         */

        private String url;
        private String id;
        private String name;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class LastestLikersBean {
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

        private String profileUrl;
        private String bio;
        private String hash;
        private long uid;
        private boolean isOrg;
        private String description;
        private boolean isOrgWhiteList;
        private String slug;
        private AvatarBeanXX avatar;
        private String name;

        public String getProfileUrl() {
            return profileUrl;
        }

        public void setProfileUrl(String profileUrl) {
            this.profileUrl = profileUrl;
        }

        public String getBio() {
            return bio;
        }

        public void setBio(String bio) {
            this.bio = bio;
        }

        public String getHash() {
            return hash;
        }

        public void setHash(String hash) {
            this.hash = hash;
        }

        public long getUid() {
            return uid;
        }

        public void setUid(long uid) {
            this.uid = uid;
        }

        public boolean isIsOrg() {
            return isOrg;
        }

        public void setIsOrg(boolean isOrg) {
            this.isOrg = isOrg;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public boolean isIsOrgWhiteList() {
            return isOrgWhiteList;
        }

        public void setIsOrgWhiteList(boolean isOrgWhiteList) {
            this.isOrgWhiteList = isOrgWhiteList;
        }

        public String getSlug() {
            return slug;
        }

        public void setSlug(String slug) {
            this.slug = slug;
        }

        public AvatarBeanXX getAvatar() {
            return avatar;
        }

        public void setAvatar(AvatarBeanXX avatar) {
            this.avatar = avatar;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public static class AvatarBeanXX {
            /**
             * id : 8f62800f7
             * template : https://pic4.zhimg.com/{id}_{size}.jpg
             */

            private String id;
            private String template;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getTemplate() {
                return template;
            }

            public void setTemplate(String template) {
                this.template = template;
            }
        }
    }
}
