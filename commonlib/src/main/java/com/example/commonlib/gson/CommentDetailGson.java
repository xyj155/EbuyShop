package com.example.commonlib.gson;

import java.util.List;

public class CommentDetailGson {

    /**
     * user : {"username":"17374131273","avatar":"/StuShop/public/avatar/13527491943.jpg"}
     * goods : {"id":26,"goodsId":30,"goodsName":" i7 8G 512G 流光金 13英寸笔记本电脑（WT-W19）","goodsPrice":"7999.00","goodsPicUrl":"http://img.iblimg.com/photo-36/3020/1860893073_360x360.jpg","goodsStock":"100"}
     * discussUser : [{"id":1,"commentId":28,"userId":1,"commentContent":"洗衣机是老婆【按发票啊【吗吗","createTime":null,"user":{"username":"17374131273","avatar":"/StuShop/public/avatar/13527491943.jpg"}},{"id":2,"commentId":28,"userId":576,"commentContent":"我 企鹅啊 打算阿三","createTime":null,"user":{"username":"13736816622","avatar":"/StuShop/public/avatar/13736816622.jpg"}},{"id":3,"commentId":28,"userId":575,"commentContent":" 啊大大阿达啊大","createTime":null,"user":{"username":"18267321598","avatar":"/StuShop/public/avatar/18267321598.jpg"}},{"id":4,"commentId":28,"userId":574,"commentContent":"阿达啊阿达啊啊 ","createTime":null,"user":{"username":"13527491943","avatar":"/StuShop/public/avatar/13527491943.jpg"}}]
     * goodsPicture : ["/StuShop/public/share/559322761.jpeg","/StuShop/public/share/Screenshot_2019-02-05-16-40-55-288_com.xingjiabi.shengsheng.png"]
     */
private String comment;
private String time;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    private UserBean user;
    private GoodsBean goods;
    private List<DiscussUserBean> discussUser;
    private List<String> goodsPicture;

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public GoodsBean getGoods() {
        return goods;
    }

    public void setGoods(GoodsBean goods) {
        this.goods = goods;
    }

    public List<DiscussUserBean> getDiscussUser() {
        return discussUser;
    }

    public void setDiscussUser(List<DiscussUserBean> discussUser) {
        this.discussUser = discussUser;
    }

    public List<String> getGoodsPicture() {
        return goodsPicture;
    }

    public void setGoodsPicture(List<String> goodsPicture) {
        this.goodsPicture = goodsPicture;
    }

    public static class UserBean {
        /**
         * username : 17374131273
         * avatar : /StuShop/public/avatar/13527491943.jpg
         */

        private String username;
        private String avatar;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }
    }

    public static class GoodsBean {
        /**
         * id : 26
         * goodsId : 30
         * goodsName :  i7 8G 512G 流光金 13英寸笔记本电脑（WT-W19）
         * goodsPrice : 7999.00
         * goodsPicUrl : http://img.iblimg.com/photo-36/3020/1860893073_360x360.jpg
         * goodsStock : 100
         */

        private int id;
        private int goodsId;
        private String goodsName;
        private String goodsPrice;
        private String goodsPicUrl;
        private String goodsStock;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(int goodsId) {
            this.goodsId = goodsId;
        }

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public String getGoodsPrice() {
            return goodsPrice;
        }

        public void setGoodsPrice(String goodsPrice) {
            this.goodsPrice = goodsPrice;
        }

        public String getGoodsPicUrl() {
            return goodsPicUrl;
        }

        public void setGoodsPicUrl(String goodsPicUrl) {
            this.goodsPicUrl = goodsPicUrl;
        }

        public String getGoodsStock() {
            return goodsStock;
        }

        public void setGoodsStock(String goodsStock) {
            this.goodsStock = goodsStock;
        }
    }

    public static class DiscussUserBean {
        /**
         * id : 1
         * commentId : 28
         * userId : 1
         * commentContent : 洗衣机是老婆【按发票啊【吗吗
         * createTime : null
         * user : {"username":"17374131273","avatar":"/StuShop/public/avatar/13527491943.jpg"}
         */

        private int id;
        private int commentId;
        private int userId;
        private String commentContent;
        private String createTime;
        private UserBeanX user;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getCommentId() {
            return commentId;
        }

        public void setCommentId(int commentId) {
            this.commentId = commentId;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getCommentContent() {
            return commentContent;
        }

        public void setCommentContent(String commentContent) {
            this.commentContent = commentContent;
        }

        public String  getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public UserBeanX getUser() {
            return user;
        }

        public void setUser(UserBeanX user) {
            this.user = user;
        }

        public static class UserBeanX {
            /**
             * username : 17374131273
             * avatar : /StuShop/public/avatar/13527491943.jpg
             */

            private String username;
            private String avatar;

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }
        }
    }
}
