(define (domain mailmen) 

  (:requirements)

  (:predicates
    (mailman ?m)            ;m is a mailman
    (point ?p)              ;p is a point
    (letter ?l)             ;Do i have to explain?
    (at ?x ?y)              ;Object x is at point y
    (has ?m ?l)             ;Mailman m has letter l
    (connection ?from ?to)  ;There's a connection going from point from to point to
    (hasMailbox ?p)         ;There's a mailbox at point p
    (delivered ?l)          ;Letter l has been delivered
  )

  (:action move             ;Take mailman m from point from to point to 
    :parameters (?m ?from ?to)
    :precondition 
    (and
        (mailman ?m)
        (point ?from)
        (point ?to)
        (at ?m ?from)           ;m must be at point from 
        (connection ?from ?to)
    )
    
    :effect 
    (and
        (at ?m ?to)           ;m is now at point to
        (not (at ?m ?from))   ;and not on point from
    )
  )

  (:action pickUp           ;Mailman m takes the letter l at point p
    :parameters (?m ?l ?p) 
    :precondition
    (and
        (mailman ?m)
        (letter ?l)
        (point ?p)
        (at ?m ?p)          ;m and l must be at the same place
        (at ?l ?p)          ;and there must be a letter at that place
    )
    
    :effect
    (and
        (has ?m ?l)         ;m now has the letter
        (not (at ?l ?p))    ;l is no longer at the place
    )
  )
  
  (:action dropOff          ;Mailman m leaves letter l at a mailbox in point p
    :parameters(?m ?l ?p)
    :precondition
    (and
        (mailman ?m)
        (letter ?l)
        (point ?p)
        (has ?m ?l)
        (at ?m ?p)
        (hasMailbox ?p)
    )
    
    :effect
    (and
        (delivered ?l)
        (not(has ?m ?l))
    )
  )         
)