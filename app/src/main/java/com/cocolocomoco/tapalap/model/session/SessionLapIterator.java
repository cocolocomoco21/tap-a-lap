package com.cocolocomoco.tapalap.model.session;

import java.util.ListIterator;


class SessionLapIterator<T> implements ListIterator<T> {
	@Override
	public boolean hasNext() {
		return false;
	}

	@Override
	public T next() {
		return null;
	}

	@Override
	public boolean hasPrevious() {
		return false;
	}

	@Override
	public T previous() {
		return null;
	}

	@Override
	public int nextIndex() {
		return 0;
	}

	@Override
	public int previousIndex() {
		return 0;
	}

	@Override
	public void remove() {

	}

	@Override
	public void set(T o) {

	}

	@Override
	public void add(T o) {

	}
}
