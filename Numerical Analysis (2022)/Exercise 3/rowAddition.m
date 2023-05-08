function L = rowAddition (d,r1,l,r2)
	L = eye(d);
	L(r1,r2) = l;
endfunction
