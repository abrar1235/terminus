package com.terminus.model;

public class Result<T, U> {

	private T success;
	private U failure;
	private String status;

	private static class Success<T, U> extends Result<T, U> {
		private T t;

		public Success(T t) {
			this.t = t;
		}

		@Override
		public T getSuccess() {
			return t;
		}

		@Override
		public U getFailure() {
			return null;
		}

		@Override
		public String getStatus() {
			return "success";
		}

		@Override
		public String toString() {
			return String.format("Success(%s)", t);
		}
	}

	private static class Failure<T, U> extends Result<T, U> {
		private U u;

		public Failure(U u) {
			this.u = u;
		}

		@Override
		public T getSuccess() {
			return null;
		}

		@Override
		public U getFailure() {
			return u;
		}

		@Override
		public String getStatus() {
			return "failure";
		}

		@Override
		public String toString() {
			return String.format("Success(%s)", u);
		}
	}

	public T getSuccess() {
		return success;
	}

	public U getFailure() {
		return failure;
	}

	public String getStatus() {
		return status;
	}

	private Result() {
	}

	public static <T, U> Result<T, U> success(T t) {
		return new Success<>(t);
	}

	public static <T, U> Result<T, U> failure(U u) {
		return new Failure<>(u);
	}

}
