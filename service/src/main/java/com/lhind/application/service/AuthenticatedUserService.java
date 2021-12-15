package com.lhind.application.service;

@FunctionalInterface
public interface AuthenticatedUserService {
    String getLoggedUsername();
}
