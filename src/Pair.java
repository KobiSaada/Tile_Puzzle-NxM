
public class Pair<L,R> {

	private L _left;
	private R _right;

	public Pair(L left, R right) {
		this._left = left;
		this._right = right;
	}
	public Pair() {
		this._left = null;
		this._right = null;
	}

	public L getLeft() { return this._left; }
	public void setLeft(L left) {this._left = left; }
	public R getRight() { return this._right; }
	public void setRight(R right) {this._right = right; }

	
	@Override
	public int hashCode() {
		if (_left==null)
			return 0;
		if(_right==null)
			return
			1;
		return _left.hashCode() ^ _right.hashCode(); }

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Pair)) return false;
		Pair pairo = (Pair) o;
		if (this._left!=null&&this._right!=null) {
			return this._left.equals(pairo.getLeft()) &&
					this._right.equals(pairo.getRight());
		}
		if (this._right==null&&this._left!=null)
			return this._left.equals(pairo.getLeft());
		if (this._left==null&&this._right!=null)
		return this._right.equals(pairo.getRight());

		return false;
	}


	

}
