package com.example.demo.Hystrix;

import java.util.concurrent.Callable;

import com.example.demo.util.UserContext;
import com.example.demo.util.UserContextHolder;

public class DelegatingUserContextCallable<T> implements Callable<T> {
	private final Callable<T> delegate;
	private UserContext originalUserContext;
	
	
	
	public DelegatingUserContextCallable(Callable<T> delegate, UserContext originalUserContext) {
		super();
		this.delegate = delegate;
		this.originalUserContext = originalUserContext;
	}



	@Override
	public T call() throws Exception {
		// TODO Auto-generated method stub
		UserContextHolder.setContext(originalUserContext);
		try {
			return delegate.call();
		}
		finally {
			this.originalUserContext=null;
		}	
	}
	
	public static<T> Callable<T> create(Callable<T> delegate,UserContext userContext) throws Exception {
		// TODO Auto-generated method stub
		return new DelegatingUserContextCallable<T> (delegate,userContext);	
	}
	
}
