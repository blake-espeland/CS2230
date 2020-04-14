package binarySearchTree;

import java.lang.Comparable;

public class AVLTree<E extends Comparable<E>> extends BinSearchTree<E> {
    @Override
    public BinTreePos<E> makeNode(E e, BinTreePos<E> par, BinTreePos<E> lt, BinTreePos<E> rt) {
        return new AVLTreeNode<E>(e,par,lt,rt);
    }

    public void rotateRight(AVLTreeNode<E> n) {
        AVLTreeNode<E> nleft = (AVLTreeNode<E>)(n.left());
        AVLTreeNode<E> lrChild = (AVLTreeNode<E>)nleft.right();

        if ( n.isRoot() ) {
            setRoot(nleft);
            nleft.setParent(null);
        } else {
            AVLTreeNode<E> par = (AVLTreeNode<E>)n.parent();
            if ( n == par.left() ) {
                par.setLeft(nleft);
            } else if ( n == par.right() ) {
                par.setRight(nleft);
            }
            nleft.setParent(par);
            // par.updateHeight();
        }
        nleft.setRight(n);
        n.setParent(nleft);
        // nleft.updateHeight();
        n.setLeft(lrChild);
        if ( lrChild != null ) {
            lrChild.setParent(n);
            lrChild.updateHeight();
        }
        n.updateHeight();
    }

    public void rotateLeft(AVLTreeNode<E> n) {
        AVLTreeNode<E> nright = (AVLTreeNode<E>)(n.right());
        AVLTreeNode<E> rlChild = (AVLTreeNode<E>)nright.left();

        if ( n.isRoot() ) {
            setRoot(nright);
            nright.setParent(null);
        } else {
            AVLTreeNode<E> par = (AVLTreeNode<E>)(n.parent());
            if ( n == par.right() ) {
                par.setRight(nright);
            } else if ( n == par.left() ) {
                par.setLeft(nright);
            }
            nright.setParent(par);
            // par.updateHeight();
        }
        nright.setLeft(n);
        n.setParent(nright);
        // nright.updateHeight();
        n.setRight(rlChild);
        if ( rlChild != null ) {
            rlChild.setParent(n);
            rlChild.updateHeight();
        }
        n.updateHeight();
    }

    public void rebalance(BinTreePos<E> pos) {
        if ( pos == null )
            return;
        AVLTreeNode<E> avlpos = (AVLTreeNode<E>)pos;
        avlpos.updateHeight();
        if ( avlpos.getBalance() == -2 ) {
            AVLTreeNode<E> avlchild = (AVLTreeNode<E>)(pos.right());
            if ( avlchild.getBalance() == 1 )
                rotateRight(avlchild);
            rotateLeft(avlpos);
        } else if ( avlpos.getBalance() == 2 ) {
            AVLTreeNode<E> avlchild = (AVLTreeNode<E>)(pos.left());
            if ( avlchild.getBalance() == -1 )
                rotateLeft(avlchild);
            rotateRight(avlpos);
        }
    }

    @Override
    public void add(E e) {
    	AVLTreePos<E> p = (AVLTreePos<E>)findPos(e);
    	if (p == null) {
    		setRoot(makeNode(e, null, null, null));
    		return;
    	}
    	int comp = e.compareTo(p.element());
    	if(comp > 0) {
    		p.setRight(makeNode(e, p, null,null));
    	}
    	if(comp < 0) {
    		p.setLeft(makeNode(e, p, null, null));
    	}
    	while(p.parent() != null) {
    		AVLTreeNode<E> avlpos = (AVLTreeNode<E>)p;
    		System.
    		if (avlpos.getBalance() == 2 || avlpos.getBalance() == -2) {
    			rebalance(p);
    			break;
    		}else {
    			p = (AVLTreeNode<E>)(p).parent();
    		}
    	}
    }

    @Override
    public String toString() {
        return "AVLRoot: " +
                (( root() == null ) ? "null" : "\n" + root().toString());
    }

    public static void main(String[] args) {
        String[] birds = { "heron", "eagle", "woodpecker", "kookaburra", "cardinal",
                "swallow", "puffin", "ostritch", "flamingo", "goose",
                "duck", "budgerigar", "magpie", "loon", "toucan", "ibis", "vulture",
                "yellowthroat", "quail", "gull", "raven", "jay", "albatross" };
        AVLTree<String> avlt = new AVLTree<String>();
        for ( String bird : birds ) {
            //System.out.println("Adding "+bird);
            avlt.add(bird);
            //System.out.println(avlt);
        }
        System.out.println(avlt);
        System.out.println();
        System.out.println("Alphabetical list of birds:");
        for ( String bird : avlt )
            System.out.println(bird);
    }
}
