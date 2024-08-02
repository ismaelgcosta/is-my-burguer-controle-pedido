package br.com.ismyburguer.core.adapter;

public interface ExistsByUsernameUseCase<T> {

    boolean existsByUsername(String username);
}
