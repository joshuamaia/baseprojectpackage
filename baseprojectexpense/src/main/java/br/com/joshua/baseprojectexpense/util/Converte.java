package br.com.joshua.baseprojectexpense.util;

public interface Converte<E, T, Res> {
	public E convertFromEntity(T request);

	public Res convertFromResponse(E entity);
}
