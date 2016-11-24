package com.meiji.daily.mvp.postslist;

/**
 * Created by Meiji on 2016/11/18.
 */
public class PostsListBean {

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
     * content : <p>那天一个读者在线找我，上来就跟我谈技术。</p><p>她：老师，我们全站启用 HTTPS 了，你觉得怎么样？</p><p>我：没什么啊，不过你们是做什么的？</p><p>她：我们是做网络直播的。</p><p>我：… 直播网站启用全站 HTTPS ？有必要吗？你们是哪一个网站？</p><p>她：斗鱼。斗鱼是目前国内首家，也是目前唯一全站升级到 HTTPS 的网络直播平台。</p><br><p>我不是网络直播的目标用户，不过，一个视频网站启用了全站 HTTPS ，说明还是有技术追求的，这个事情也是有技术含量的，可以聊一下。</p><p>作为一个不懂技术，尤其是「不写代码」的离任 CTO，跟人家讨论技术是挺难为情的事儿。万一露馅了怎么办？所以，有不对的地方，欢迎指出。</p><p>HTTPS 这事儿，国内不少网站也就是用来保护一下用户数据，用户登录的时候启用而已。更多时候，出于性能开销上的折衷考虑，基本上都是非加密传输数据。多媒体文件，尤其是流媒体，明文传输似乎不会出现什么安全问题，然而，实际上也会产生非常严重的后果，比如被恶意劫持流量，页面上动不动插入一个小广告。而访问者还以为这是受访站提供的广告。如果是正常的广告还可以忍，然而色情赌博虚假医疗广告比比皆是。这当然不是好的用户体验。</p><p>所以，在我们这个特定环境下，重视 HTTPS 是应该的，更不用说出于保护用户隐私等目的而去部署 HTTPS 了。</p><p>其实，作为一个技术团队负责人(CTO)，今年至少有几个关于 HTTPS 的标志性事件应该引起你的关注（要不可真不太合格）：</p><ol><li>YouTube 启用 HTTPS 加密，全站 97% 的流量均通过 HTTPS 传输(还有 3% 是因为要兼容旧设备)。</li><li>Netflix 宣布将使用 HTTPS 加密流视频。流视频的加密传输是个技术难题，但 Netflix 无疑找到了一个平衡。</li><li>苹果公司在 WWDC 2016 宣布，到 2017 年 1 月 1 日 App Store 中的所有应用都必须启用 App Transport Security 安全功能。App Transport Security（ATS）是苹果在 iOS 9 中引入的一项隐私保护功能，屏蔽明文 HTTP 资源加载，连接必须经过 HTTPS。</li></ol><p>从全球范围看，推动 HTTPS 技术的公司里，Google 最为积极，专门做了一个专题页面监控「各大热门网站的 HTTPS 实施情况」，而 Google 全站到目前已经有超过 85% 的链接启用了加密。对比之下，中国的互联网公司对这方面的重视程度并不够。所以，这位斗鱼的朋友带着一点自豪感来跟我讲这个事儿是有一定缘由的。</p><p><b>重视 HTTPS 是一种义务和责任</b>。</p><p>见微知著，一个公司如果能在技术层面比别人做得更进一步，愿意做投入，去做一些改变用户体验的事情，其他地方想必也会花心思，据斗鱼介绍，一个直播房间已经可以支撑百万人同时发弹幕。单从技术的角度讲，用户对弹幕系统的要求其实也挺高的，需要能支撑高并发、实时性的系统，做起来同样存在难度和挑战。<br></p><p>一个有追求的技术人在这样的团队里会遇到技术上的挑战，当然工作成果也会得到重视。</p><p>这也是我写这篇文章的主要原因。毫无疑问，本文由斗鱼直播平台约稿。</p>
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

    public static final String POSTSLISTBEAN_TITLEIMAGE = "titleImage";
    public static final String POSTSLISTBEAN_TITLE = "title";
    public static final String POSTSLISTBEAN_SLUG = "slug";

    private boolean isTitleImageFullScreen;
    private String rating;
    private String sourceUrl;
    private String publishedTime;
    private LinksBean links;
    private AuthorBean author;
    private String url;
    private String title;
    private String titleImage;
    private String summary;
    private String content;
    private String state;
    private String href;
    private MetaBean meta;
    private String commentPermission;
    private String snapshotUrl;
    private boolean canComment;
    private int slug;
    private int commentsCount;
    private int likesCount;

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

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public String getPublishedTime() {
        return publishedTime;
    }

    public void setPublishedTime(String publishedTime) {
        this.publishedTime = publishedTime;
    }

    public LinksBean getLinks() {
        return links;
    }

    public void setLinks(LinksBean links) {
        this.links = links;
    }

    public AuthorBean getAuthor() {
        return author;
    }

    public void setAuthor(AuthorBean author) {
        this.author = author;
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

    public String getTitleImage() {
        return titleImage;
    }

    public void setTitleImage(String titleImage) {
        this.titleImage = titleImage;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
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

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
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

    public String getSnapshotUrl() {
        return snapshotUrl;
    }

    public void setSnapshotUrl(String snapshotUrl) {
        this.snapshotUrl = snapshotUrl;
    }

    public boolean isCanComment() {
        return canComment;
    }

    public void setCanComment(boolean canComment) {
        this.canComment = canComment;
    }

    public int getSlug() {
        return slug;
    }

    public void setSlug(int slug) {
        this.slug = slug;
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

    public static class AuthorBean {
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
        private AvatarBean avatar;
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

        public AvatarBean getAvatar() {
            return avatar;
        }

        public void setAvatar(AvatarBean avatar) {
            this.avatar = avatar;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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
}
