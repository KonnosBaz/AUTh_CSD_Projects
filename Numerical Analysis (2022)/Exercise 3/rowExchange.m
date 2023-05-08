function P = rowExchange (d,r1,r2)
	P = eye(d);
	P(r1,r1) = 0;
	P(r1,r2) = 1;
	P(r2,r2) = 0;
	P(r2,r1) = 1;
endfunction
