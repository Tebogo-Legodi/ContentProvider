# react-native-content-provider

## Getting started

`$ npm install git+https://github.com/Tebogo-Legodi/react-native-content-provider.git --save`

## Usage
```javascript
import ContentProvider from 'react-native-content-provider';

// TODO: What to do with the module?
const getContent = async ()=>{
    const contentProviderUrl = 'content://BluContentProvider/device';
    const exists = await ContentProvider.getData(bludroidUri);
    
    if(exists){
      const data = JSON.parse(exists);
      alert(JSON.stringify(data));
    }
    else{
      alert('does not exist');
    }
  }
```
