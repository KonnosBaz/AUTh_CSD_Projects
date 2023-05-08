function L = CholeskyDecomp (A)
	n = size(A,1);
	L = zeros(n);

	for	j=1:n
		for	i = j:n
			if (i==j)
				q = 0;
				for k = 1:j-1
					q+=(L(i,k)).^2;
				endfor
				L(i,j) = sqrt(A(i,j) - q);
			else
				q = 0;				
				for k = 1:j-1
						q+=L(i,k).*L(j,k);
				endfor
				L(i,j) = (1/L(j,j))*(A(i,j)-q);
			endif
		endfor
	endfor

endfunction
