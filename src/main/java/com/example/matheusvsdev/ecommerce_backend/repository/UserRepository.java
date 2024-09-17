package com.example.matheusvsdev.ecommerce_backend.repository;

import com.example.matheusvsdev.ecommerce_backend.entities.User;
import com.example.matheusvsdev.ecommerce_backend.projection.UserDetailsProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    @Query(nativeQuery = true, value = """
			SELECT client.email AS username, client.password, tb_role.id AS roleId, tb_role.authority
			FROM client
			INNER JOIN user_role ON client.id = user_role.user_id
			INNER JOIN tb_role ON tb_role.id = user_role.role_id
			WHERE client.email = :email
		""")
    List<UserDetailsProjection> searchUserAndRolesByEmail(String email);
}
