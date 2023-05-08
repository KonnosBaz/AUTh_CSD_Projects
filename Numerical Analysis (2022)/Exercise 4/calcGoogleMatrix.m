function G = calcGoogleMatrix (A)

	q = 0.15;

	n = size(A,1);
	
	G =zeros(n,n);
	
	for	i = 1:n
		for j = 1:n
			G(i,j) = (q/n) + (A(j,i)*(1-q))/sum(A,2)(j);
		endfor
	endfor
	

endfunction
