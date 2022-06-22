package com.terminus.serviceimpl;

import com.terminus.entity.UserDTO;
import com.terminus.model.User;
import com.terminus.repository.IUserRepository;
import com.terminus.util.ApplicationUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SuppressWarnings({"rawtypes", "unchecked"})
class UserServiceTest {

    @InjectMocks
    UserService subject;

    @Mock
    IUserRepository userRepository;

    @Mock
    ApplicationUtil applicationUtil;

    @Mock
    EntityManager manager;

    @Mock
    CriteriaBuilder builder;

    @Mock
    TypedQuery typedQuery;

    @ParameterizedTest
    @CsvSource({
            "email",
            "name"
    })
    void getUserByKeyTest(String key) {
        CriteriaQuery query = mock(CriteriaQuery.class);
        Root root = mock(Root.class);
        Tuple tuple = mock(Tuple.class);
        Path path = mock(Path.class);
        when(manager.getCriteriaBuilder()).thenReturn(builder);
        when(builder.createTupleQuery()).thenReturn(query);
        when(query.from(UserDTO.class)).thenReturn(root);
        when(query.multiselect(any(Selection.class))).thenReturn(query);
        when(query.where(any(Predicate.class))).thenReturn(query);
        when(manager.createQuery(any(CriteriaQuery.class))).thenReturn(typedQuery);
        when(typedQuery.getSingleResult()).thenReturn(tuple);
        when(tuple.get(anyInt())).thenReturn("test");
        when(root.get(anyString())).thenReturn(path);
        assertNotNull(subject.getUserByKey(key, "test"));
    }

    @Test
    void getUserByKeyExceptionTest() {
        when(manager.getCriteriaBuilder()).thenThrow(new RuntimeException("Test"));
        assertNotNull(subject.getUserByKey("email", "test"));
    }

    @ParameterizedTest
    @CsvSource({
            "id",
            "name"
    })
    void getAllUserByKeyTest(String key) {
        CriteriaQuery query = mock(CriteriaQuery.class);
        Root root = mock(Root.class);
        Tuple tuple = mock(Tuple.class);
        Path path = mock(Path.class);
        when(manager.getCriteriaBuilder()).thenReturn(builder);
        when(builder.createTupleQuery()).thenReturn(query);
        when(query.from(UserDTO.class)).thenReturn(root);
        when(query.multiselect(any(Selection.class))).thenReturn(query);
        when(query.where(any(Predicate.class))).thenReturn(query);
        when(manager.createQuery(any(CriteriaQuery.class))).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(List.of(tuple));
        when(tuple.get(anyInt())).thenReturn("test");
        when(root.get(anyString())).thenReturn(path);
        assertNotNull(subject.getAllUserByKey(key, "test"));
    }

    @Test
    void getAllUserByKeyExceptionTest() {
        when(manager.getCriteriaBuilder()).thenThrow(new RuntimeException("Test"));
        assertNotNull(subject.getAllUserByKey("email", "test"));
    }

    @Test
    void addUserTest() throws Exception {
        User user = User.builder()
                .name("test")
                .email("test")
                .profession("test")
                .password("test")
                .build();

        UserDTO userDTO = UserDTO.builder()
                .id(UUID.randomUUID().toString())
                .name("test")
                .email("test")
                .profession("test")
                .password("test")
                .build();
        when(applicationUtil.encryptPassword(anyString())).thenReturn("encrypted");
        when(userRepository.save(any(UserDTO.class)))
                .thenReturn(userDTO);
        when(applicationUtil.generateToken(anyString())).thenReturn("token");
        assertNotNull(subject.addUser(user));
    }

    @Test
    void addUserExceptionTest() throws Exception {
        User user = User.builder()
                .build();
        when(applicationUtil.encryptPassword(anyString())).thenReturn("encrypted");
        when(applicationUtil.generateToken(anyString())).thenReturn("token");
        assertNotNull(subject.addUser(user));
    }

    @ParameterizedTest
    @CsvSource({"1", "0"})
    void updateUserTest(int count){
        Map<String, Object> request = new HashMap<>();
        request.put("id", "test");
        CriteriaUpdate update = mock(CriteriaUpdate.class);
        Root root = mock(Root.class);
        Predicate predicate = mock(Predicate.class);

        when(manager.getCriteriaBuilder()).thenReturn(builder);
        when(builder.createCriteriaUpdate(any())).thenReturn(update);
        when(update.from(UserDTO.class)).thenReturn(root);
        when(builder.equal(any(Expression.class), anyString())).thenReturn(predicate);
        when(update.where(any(Predicate.class))).thenReturn(update);
        when(manager.createQuery(any(CriteriaUpdate.class))).thenReturn(typedQuery);
        when(typedQuery.executeUpdate()).thenReturn(count);
        assertNotNull(subject.updateUser(request));
    }

    @Test
    void updateUserExceptionTest(){
        Map<String, Object> request = new HashMap<>();
        CriteriaUpdate update = mock(CriteriaUpdate.class);
        Root root = mock(Root.class);
        Predicate predicate = mock(Predicate.class);

        when(manager.getCriteriaBuilder()).thenReturn(builder);
        when(builder.createCriteriaUpdate(CriteriaUpdate.class)).thenReturn(update);
        when(update.from(UserDTO.class)).thenReturn(root);
        when(builder.equal(any(Expression.class), anyString())).thenReturn(predicate);
        when(update.where(any(Predicate.class))).thenReturn(update);
        when(manager.createQuery(any(CriteriaUpdate.class))).thenReturn(typedQuery);
        when(typedQuery.executeUpdate()).thenReturn(1);
        assertNotNull(subject.updateUser(request));
    }

    @Test
    void deleteUserTest(){
        doNothing().when(userRepository).deleteById(anyString());
        assertNotNull(subject.deleteUser("test"));
    }

    @Test
    void deleteUserExceptionTest(){
        doThrow(new RuntimeException("test")).when(userRepository).deleteById(anyString());
        assertNotNull(subject.deleteUser("test"));
    }
}
