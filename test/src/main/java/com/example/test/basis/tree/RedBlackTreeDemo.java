package com.example.test.basis.tree;
/**
 * @Description 
 * @author leiel
 * @Date 2020/6/24 9:17 AM
 */
public class RedBlackTreeDemo {

    private Node node;

    class Node {
        /**
         * 1为黑色，0为红色
         */
        public Integer color;

        public Integer var;

        public Node right;

        public Node left;

        public Node parent;

        public Node(Integer var) {
            this.var = var;
        }
    }

    /**
     * 中序遍历打印二叉树
     */
    public void inOrderPrint() {

        inOrder(this.node);

    }

    private void inOrder(Node node) {
        if(node != null) {
            inOrder(node.left);
            System.out.println("key" + node.var);
            inOrder(node.right);
        }
    }

    /**
     * 获取父亲节点
     */
    public Node parentOf(Node node) {

        if(node != null) {
            return node.parent;
        }
        return null;

    }

    /**
     * 设置当前节点为红色
     */
    public void setRed(Node node) {

        if(node != null) {
            node.color = 0;
        }

    }

    /**
     * 设置当前节点为黑色
     */
    public void setBlack(Node node) {

        if(node != null) {
            node.color = 1;
        }

    }

    /**
     * 判断当前阶段是否为红色
     * @param node
     * @return
     */
    private boolean isRed (Node node) {

        if(node != null) {
            return node.color == 0;
        }
        return false;

    }

    /**
     * 判断当前阶段是否为黑色
     * @param node
     * @return
     */
    private boolean isBlack (Node node) {

        if(node != null) {
            return node.color == 1;
        }
        return false;

    }


    //插入元素
    public void insert(Node node, int value) {

        if(node.parent  == null) {
            node.parent = new Node(value);
        }

        if(node.right == null) {
            node.right = new Node(value);
        }else {
            insert(node, value);
        }

        if(node.left == null) {
            node.left = new Node(value);
        } else {
            insert(node, value);
        }

    }

    /**
     * 左旋 传入旋转节点
     */
    public void leftRotate(Node E) {

        Node S = E.right;

        //1.将E的右子节点指向S的左子节点，将S的左子节点更新为E
        E.right = S.left;

        if(S.left != null) {
            S.left.parent = E;
        }

        //2.如果E存在父节点，则将S的父节点指向E的父节点，如果E在E的父节点的左边，则将S赋予E的父节点的左子树
        if(E.parent != null) {
            S.parent = E.parent;

            if(E == E.parent.left) {
                E.parent.left = S;
            } else {
                E.parent.right = S;
            }
        } else {
            this.node = S;
        }

        //3.将E的父节点设置为S,S的左子节点为E
        E.parent = S;
        S.left = E;

    }

    /**
     * 右旋 传入旋转节点
     */
    public void rightRotate(Node S) {

        Node E = S.left;

        //1.将E的右子节点设置为S的左子节点，将E的右子节点更新为S
        S.left = E.right;

        if(E.right != null) {
            E.right.parent = S;
        }

        //2.如果S存在父节点，设置为E的父节点，且保持S所在的位置，设置E
        if(S.parent != null) {

            E.parent = S.parent;

            if(S == S.parent.left) {
                S.parent.left = E;
            }else {
                S.parent.right = E;
            }

        }else {
            this.node = E;
        }

        //3.将S的父节点设置为E, E的右子节点设置为S
        S.parent = E;
        E.right = S;

    }

    /**
     * 插入新节点修复红黑树的方法
     * 插入后修复红黑树的情况:
     *      ---情况1:红黑树为空树，将节点变为黑色
     *      ---情况2:红黑树值已经存在，不做任何处理
     *      ---情况3:插入节点的父节点为黑色，不做任何处理
     *
     *      ---情况4:插入节点的父节点为红色
     *          ---情况4.1:叔叔节点存在，并且为红色:将父节点和叔叔节点更新为黑色，将爷爷节点更新为红色，然后以爷爷节点为当前节点做下一轮处理递归
     *          ---情况4.2:叔叔节点不存在，或者为黑色，父节点为爷爷节点的左子树
     *              ---情况4.2.1:插入节点为其父节点的左子节点（LL双红）,将父亲节点染色为黑色，爷爷节点染色成红色，右旋（爷爷节点）
     *              ---情况4.2.2:插入节点为其父节点的右子节点（LR双红）,先左旋（父节点）变成LL双红再按照4.2.1进行处理 然后以父亲节点为当前节点递归
     *          ---情况4.3:叔叔节点不存在，或者为黑色，父节点为爷爷节点的右子树
     *              ---情况4.3.1:插入节点为其父节点的右子节点（RR双红）,将父亲节点染色为黑色，爷爷节点染色成红色，左旋（爷爷节点）
     *              ---情况4.3.2:插入节点为其父节点的左子节点（RL双红）,先右旋（父节点）变成RR双红再按照4.3.1进行处理 然后以父亲节点为当前节点递归
     *
     */
    public void insertFixup(Node node) {

        //设置为黑色
        this.node.color = 1;

        Node parent = parentOf(node);
        Node gparent = parentOf(parent);

        //插入节点的父节点为红色
        if(parent != null && isRed(parent)) {

            Node uncle;

            //情况4.1:叔叔节点存在，并且为红色
            if(gparent.left == parent) {
                uncle = gparent.right;
                if(uncle != null && isRed(uncle)) {
                    //将父节点和叔叔节点更新为黑色，将爷爷节点更新为红色
                    setBlack(parent);
                    setBlack(uncle);
                    setRed(gparent);
                    insertFixup(gparent);
                    return;
                }
                //情况4.2:将父亲节点染色为黑色，爷爷节点染色成红色，右旋（爷爷节点）
                if(uncle == null || isBlack(uncle)) {

                    //插入节点为其父节点的左子节点（LL双红）先右旋
                    if(parent.left == node) {
                        setBlack(parent);
                        setRed(gparent);
                        rightRotate(parent);
                        return;

                    }else{
                        //先左旋（父节点）变成LL双红再按照4.2.1进行处理
                        leftRotate(parent);
                        insertFixup(parent);
                        return;

                    }

                }

            }else {
                //父节点在右边
                uncle = gparent.right;
                if(uncle != null && isRed(uncle)) {
                    //将父节点和叔叔节点更新为黑色，将爷爷节点更新为红色
                    setBlack(parent);
                    setBlack(uncle);
                    setRed(gparent);
                    insertFixup(gparent);
                    return;
                }
                //情况4.3:叔叔节点不存在，或者为黑色，父节点为爷爷节点的左子树
                if(uncle == null || isBlack(uncle)) {

                    //插入节点为其父节点的左子节点（RL双红）
                    if(parent.left == node) {

                        //RL双红 先右旋（父节点）变成RR双红再按照4.3.1进行处理
                        rightRotate(gparent);
                        insertFixup(parent);
                        return;

                    }else{
                        //RR双红 将父亲节点染色为黑色，爷爷节点染色成红色 右旋
                        setBlack(parent);
                        setRed(gparent);
                        leftRotate(parent);
                        return;

                    }

                }

            }

        }

    }

}

