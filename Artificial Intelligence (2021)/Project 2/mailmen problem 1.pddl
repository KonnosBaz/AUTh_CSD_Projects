(define (problem mailmen1)
  (:domain mailmen)

  (:objects p1 p2 p3 p4 p5 p6 p7 p8 p9 p10 p11 p12 p13 p14 m l1 l2)

  
  (:init
    ;defining points
    (point p1)(point p2)(point p3)(point p4)(point p5)
    (point p6)(point p7)(point p8)(point p9)(point p10)
    (point p11)(point p12)(point p13)(point p14)
    ;defining mailmen
    (mailman m)
    ;defining letters
    (letter l1)
    (letter l2)
    ;specifying letters and mailman position
    (at m p14)
    (at l1 p13)
    (at l2 p10)
    
    ;defining roads
    (connection p1 p2)(connection p2 p1)    
    (connection p2 p3)(connection p3 p2)
    (connection p2 p11)(connection p11 p2)
    (connection p3 p4)(connection p4 p3)
    (connection p4 p5)(connection p5 p4)
    (connection p5 p12)(connection p12 p5)
    (connection p11 p6)(connection p6 p11)
    (connection p11 p9)(connection p9 p11)
    (connection p10 p6)(connection p6 p10)
    (connection p10 p9)(connection p9 p10)
    (connection p10 p8)(connection p8 p10)
    (connection p8 p13)(connection p13 p8)
    (connection p13 p14)(connection p14 p13)
    ;defining metros
    (connection p2 p3)
    (connection p3 p7)
    (connection p7 p8)
    (connection p8 p6)
    (connection p6 p2)
    
    (connection p6 p7)
    
    
    (hasMailbox p7)
  )

  
  (:goal 
    (and
        (delivered l1)
        (delivered l2)
    )
  )
)