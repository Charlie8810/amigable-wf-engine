import React, { Component } from 'react';
import PropTypes from 'prop-types';

class ProcessListItem extends Component {

    constructor(props) {
        super(props);
        this.state = {}
    }


    render(){

        return (
                <li className="collection-item avatar">
                  <i className="material-icons circle red">play_arrow</i>
                  <span className="title">{this.props.name}</span>
                  <p>{this.props.description}</p>
                  <a href="#!" title="Editar Proceso" className="secondary-content"><i className="material-icons">device_hub</i></a>
                </li>
        );
    }


}

ProcessListItem.propTypes = {
    id: PropTypes.number,
    name: PropTypes.string,
    description: PropTypes.string
};

export default ProcessListItem;