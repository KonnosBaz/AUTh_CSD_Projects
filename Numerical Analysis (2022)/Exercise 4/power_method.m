function [eigen_vector, eigen_value] = power_method(A, tolerance, max_iter)
  k = 0;
  n = size(A, 1);
  eigen_vector_old = rand(n, 1);
  
  do
    % Calculate a new approximation for the dominant eigen vector
    eigen_vector_new = A * eigen_vector_old;
    
    % Calculate a new approximate dominant eigen value
    eigen_value = (eigen_vector_new' * eigen_vector_old)/(eigen_vector_old' * eigen_vector_old);
    
    % Calculate the error
    err = norm(eigen_vector_new / norm(eigen_vector_new) - eigen_vector_old / norm(eigen_vector_old));
    
    % Get ready for the next iteration
    eigen_vector_old = eigen_vector_new;
    k = k + 1;
  until (( err < tolerance ) | (k > max_iter));
  
  % Output the number of iterations
  k
  
  % Assign the values to be returned
  eigen_vector = eigen_vector_new / norm(eigen_vector_new);
  
  % Check if the solution did not converge
  if (k > max_iter)
    disp("Error: Method did not converge");
    eigen_vector = [];
    eigen_value = [];
  endif
endfunction