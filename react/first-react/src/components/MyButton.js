import { useState } from 'react';
function MyButton({counter,onClick}) {
//   const [counter, setCounter] = useState(0);
//   function handleClick() {
//     setCounter(counter + 1);
//     console.log(counter);
//     //alert('You clicked me!');
//   }
  return (
    <button onClick={onClick}>click {counter} </button>
  );
}

export default MyButton;