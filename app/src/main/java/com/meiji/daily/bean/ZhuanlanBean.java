package com.meiji.daily.bean;

import java.util.List;

/**
 * Created by Meiji on 2016/11/16.
 */

public class ZhuanlanBean {

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

    private int followersCount;
    private CreatorBean creator;
    private String activateState;
    private String href;
    private boolean acceptSubmission;
    private boolean firstTime;
    private String pendingName;
    private AvatarBeanX avatar;
    private boolean canManage;
    private String description;
    private int nameCanEditUntil;
    private String reason;
    private int banUntil;
    private String slug;
    private String name;
    private String url;
    private String intro;
    private int topicsCanEditUntil;
    private String activateAuthorRequested;
    private String commentPermission;
    private boolean following;
    private int postsCount;
    private boolean canPost;
    private List<TopicsBean> topics;
    private List<PostTopicsBean> postTopics;
    private List<?> pendingTopics;

    public int getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(int followersCount) {
        this.followersCount = followersCount;
    }

    public CreatorBean getCreator() {
        return creator;
    }

    public void setCreator(CreatorBean creator) {
        this.creator = creator;
    }

    public String getActivateState() {
        return activateState;
    }

    public void setActivateState(String activateState) {
        this.activateState = activateState;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public boolean isAcceptSubmission() {
        return acceptSubmission;
    }

    public void setAcceptSubmission(boolean acceptSubmission) {
        this.acceptSubmission = acceptSubmission;
    }

    public boolean isFirstTime() {
        return firstTime;
    }

    public void setFirstTime(boolean firstTime) {
        this.firstTime = firstTime;
    }

    public String getPendingName() {
        return pendingName;
    }

    public void setPendingName(String pendingName) {
        this.pendingName = pendingName;
    }

    public AvatarBeanX getAvatar() {
        return avatar;
    }

    public void setAvatar(AvatarBeanX avatar) {
        this.avatar = avatar;
    }

    public boolean isCanManage() {
        return canManage;
    }

    public void setCanManage(boolean canManage) {
        this.canManage = canManage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNameCanEditUntil() {
        return nameCanEditUntil;
    }

    public void setNameCanEditUntil(int nameCanEditUntil) {
        this.nameCanEditUntil = nameCanEditUntil;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getBanUntil() {
        return banUntil;
    }

    public void setBanUntil(int banUntil) {
        this.banUntil = banUntil;
    }

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public int getTopicsCanEditUntil() {
        return topicsCanEditUntil;
    }

    public void setTopicsCanEditUntil(int topicsCanEditUntil) {
        this.topicsCanEditUntil = topicsCanEditUntil;
    }

    public String getActivateAuthorRequested() {
        return activateAuthorRequested;
    }

    public void setActivateAuthorRequested(String activateAuthorRequested) {
        this.activateAuthorRequested = activateAuthorRequested;
    }

    public String getCommentPermission() {
        return commentPermission;
    }

    public void setCommentPermission(String commentPermission) {
        this.commentPermission = commentPermission;
    }

    public boolean isFollowing() {
        return following;
    }

    public void setFollowing(boolean following) {
        this.following = following;
    }

    public int getPostsCount() {
        return postsCount;
    }

    public void setPostsCount(int postsCount) {
        this.postsCount = postsCount;
    }

    public boolean isCanPost() {
        return canPost;
    }

    public void setCanPost(boolean canPost) {
        this.canPost = canPost;
    }

    public List<TopicsBean> getTopics() {
        return topics;
    }

    public void setTopics(List<TopicsBean> topics) {
        this.topics = topics;
    }

    public List<PostTopicsBean> getPostTopics() {
        return postTopics;
    }

    public void setPostTopics(List<PostTopicsBean> postTopics) {
        this.postTopics = postTopics;
    }

    public List<?> getPendingTopics() {
        return pendingTopics;
    }

    public void setPendingTopics(List<?> pendingTopics) {
        this.pendingTopics = pendingTopics;
    }

    public static class CreatorBean {
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

        private String profileUrl;
        private String bio;
        private String hash;
        private long uid;
        private boolean isOrg;
        private String description;
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
             * id : ba332a401
             * template : https://pic2.zhimg.com/{id}_{size}.jpg
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

    public static class AvatarBeanX {
        /**
         * id : v2-5410cdcdc7fb1556a27d0ddc4734e64b
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

    public static class TopicsBean {
        /**
         * url : https://www.zhihu.com/topic/19550517
         * id : 19550517
         * name : 互联网
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

    public static class PostTopicsBean {
        /**
         * postsCount : 27
         * id : 153
         * name : 用户体验
         */

        private int postsCount;
        private int id;
        private String name;

        public int getPostsCount() {
            return postsCount;
        }

        public void setPostsCount(int postsCount) {
            this.postsCount = postsCount;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
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
