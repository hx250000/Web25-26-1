import logo from './logo.svg';
import './App.css';
import MyButton from './components/MyButton';
import LoginForm from './components/LoginForm';
import AdminPanel from './components/AdminPanel';
import { useState } from 'react';

const user = {
  name: 'Hedy Lamarr',
  imageUrl: 'https://i.postimg.cc/4NMrN002/shuaige.jpg',
  imageSize: 200,
};

function App() {
  let content;
  let isLoggedIn = false; // Change this to false to test the other component
  const products = [
    { title: 'Cabbage', id: 1 },
    { title: 'Garlic', id: 2 },
    { title: 'Apple', id: 3 },
  ];
  const listItems = products.map(product =>
    <li key={product.id}>
      {product.title}
    </li>
  );
  if (isLoggedIn) {
    content = <AdminPanel />;
  } else {
    content = <LoginForm />;
  }

  const [counter, setCounter] = useState(0);
  function handleClick() {
    setCounter(counter + 1);
    console.log(counter);
    //alert('You clicked me!');
  }
  
  return (
    <div className="App">
      <header className="App-header">
        <h1>欢迎来到 React 世界！</h1>
        <h2>Counters that update together</h2>
        <MyButton counter={counter} onClick={handleClick}/>
        <MyButton counter={counter} onClick={handleClick}/>
        {content}
        <ul>{listItems}</ul>
      </header>
    </div>
  );
}

export default App;
