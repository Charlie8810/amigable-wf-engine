import axios from 'axios';
import '../conf/axios';

const process = {

    async listAll(){
        const options = { url: '/all-defs', method:'get',  timeout: 20000 };
        const response = await axios(options);
        const data = await response.data;
        return data;
    }
}


export default process;