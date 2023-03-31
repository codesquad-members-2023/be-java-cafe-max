package kr.codesqaud.cafe.domain.user.repository.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import kr.codesqaud.cafe.domain.user.entity.User;

@Repository
public class MemoryUserRepositoryImpl {

	private Map<Integer, User> users = new ConcurrentHashMap<>();
}
