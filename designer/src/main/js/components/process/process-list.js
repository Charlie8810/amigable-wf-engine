import React, { Component } from 'react';
import ProcessListItem from './process-list-item';
import ProcessApi from '../../api/process';
//import PropTypes from 'prop-types';

class ProcessList extends Component {


    /*static propTypes = {
        data: PropTypes.object.isRequired
    }*/

    constructor(props) {
        super(props);
        this.state = {
            loading: true,
            processes: []
        }
    }

    async componentDidMount(){
        const processes = await ProcessApi.listAll();
        this.setState({
            processes,
            loading: false
        });

    }

    render(){

        return (

              <ul className="collection">
                {this.state.loading && (
                    <li className="collection-item avatar">
                        <i className="material-icons preload-circle"></i>
                        <div className="preload-line-title"></div>
                        <div className="preload-line-content"></div>
                    </li>
                )}

                {this.state.processes.map(
                    process => <ProcessListItem key={process.id} {...process}/>
                )}


              </ul>

        );
    }


}

export default ProcessList;