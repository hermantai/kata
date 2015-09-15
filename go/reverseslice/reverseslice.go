/*
 * reverseslice reverses a slice of integers
 */
package main

import (
	"fmt"
)

func reverseslice(s []int) {
	n := len(s)

	for i, j := 0, n-1; i < j; i, j = i+1, j-1 {
		s[i], s[j] = s[j], s[i]
	}
}

func main() {
	s := []int{1, 2, 3, 4, 5}
	fmt.Println("Original:", s)
	reverseslice(s)
	fmt.Println("After:", s)
}
