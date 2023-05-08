function L = ndSignChange (P)
	n = rank(P);
	L = P;
	for i=1:n
		for j=1:n
			if(i!=j)
				L(i,j) = - L(i,j);
			endif
		endfor
	endfor
endfunction
